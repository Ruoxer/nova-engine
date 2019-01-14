package com.war3.nova.core.scheduler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsNode;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.CoreSelector;
import com.war3.nova.core.actuator.ProcessActuator;
import com.war3.nova.core.enumeration.ActuatorType;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.enumeration.RouteType;
import com.war3.nova.core.factory.ActuatorFactory;
import com.war3.nova.core.factory.SelectorFactory;
import com.war3.nova.core.service.util.Nodes;
import com.war3.nova.core.service.util.Processes;
import com.war3.nova.core.thread.ThreadPool;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Strings;

/**
 * 流程调度器
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午8:37:00
 * @version 1.0
 */
public class SyncProcessScheduler extends AbstractProcessScheduler {

    private final static Logger logger = LoggerFactory.getLogger(SyncProcessScheduler.class);

    @Autowired
    @Qualifier("asyncProcessThreadPool")
    private ThreadPool threadPool;
    
    @Override
    public Nova dispatch(Nova nova) throws NovaException {
        
        
        ProcessActuator actuator = ActuatorFactory.factory().getProcessActuator(ActuatorType.PROCESS);

        CoreSelector<NvNode> nodeRouteSelector = SelectorFactory.factory().getRouteSelector(RouteType.NODE);
        /*
         * 当前节点为空，应为以下几种情况
         * 1：自动流程第一次调用
         * 2：自动流程异常后重新调起未传入当前节点ID
         * 3：人工流程异常
         */
        if (Strings.isEmpty(nova.getNodeId())) {
        
            // 1：自动流程第一次调用
            if (Strings.isEmpty(nova.getNodeInstId())) {
                NvNode node = nodeRouteSelector.select(nova);
                nova.setNodeId(node.getId());
                nova = super.processSchedule(actuator, nova);
            } else {
                // 2：自动流程异常后重新调起未传入当前节点ID
                // 3：人工流程异常
                NvInsNode insNode = Novas.ofNullableElse(Nodes.getByInstaceId(nova.getNodeInstId()), new NvInsNode());
                nova.setNodeId(insNode.getNodeId());
                nova.setNodeStatus(insNode.getStatus());
                if (ProcessConstants.STATUS_END.equals(insNode.getStatus())) {
                    logger.warn("流程实例[{}], 节点实例[{}], 已执行结束", nova.getProcessInstId(), nova.getNodeInstId());
                } else if (Strings.isNotEmpty(insNode.getInstanceId())) {
                    logger.warn("流程实例[{}], 节点实例[{}], 当前状态为[{}], 当前重新执行!", nova.getProcessInstId(), nova.getNodeInstId(), nova.getNodeStatus());
                    super.processSchedule(actuator, nova);
                }
            }
            
        } 
        /*
         * 当前节点不为空，应为以下几种情况
         * 1：人工节点提交
         * 2：自动流程异常后重新调起
         */
        else {
            
            NvNode currNode = ProcessConfigContext.getContext().getProcess().getNode(nova.getNodeId());
            
            /*
             *  第一种情况处理，即人工节点提交，此时需要判断以下情况
             *  1：人工第一个节点提交
             *  2：非人工第一个节点提交
             */
            if (NodeType.compare(NodeType.ARTI, currNode.getType())) {
                NvNode startNode = this.getPreStartNode(ProcessConfigContext.getContext().getProcess(), nova.getNodeId());
                // 当前节点前置节点为开始节点， 单独处理
                if (Objects.nonNull(startNode)) {
                    doStartNodeScheduler(nova, startNode);
                } 
            } 
            /*
             * 第二种情况， 自动流程异常后重新调起
             */
            nova = super.processSchedule(actuator, nova);
            
            /*else if (NodeType.compare(NodeType.AUTO, currNode.getType())) {
                nova = super.processSchedule(actuator, nova);
            }*/
        }
        
        
        if (ProcessConstants.STATUS_APPROVAL.equals(nova.getNodeStatus())) {
            logger.info("流程实例[{}], 当前节点[{}]状态为审批中, 请进行人工审批!", nova.getProcessInstId(), nova.getNodeId());
            nova.setProcessStatus(ProcessConstants.STATUS_APPROVAL);
        } else if (ProcessConstants.STATUS_END.equals(nova.getNodeStatus())) {
            
            NvNode nextNode = nodeRouteSelector.select(nova);
            initNextNode(nova, nextNode);
            try {
                if (NodeType.compare(NodeType.AUTO, nextNode.getType()) || NodeType.compare(NodeType.END, nextNode.getType())) {
                        logger.info("流程实例[{}], 当前节点[{}]为自动节点或结束节点, 进入异步执行", nova.getProcessInstId(), nextNode.getId());
                        threadPool.add(new AsyncProcessScheduler(nova, ProcessConfigContext.getContext().getProcess()));
                } else {
                    ProcessConfigContext.getContext().setNodeId(nextNode.getId());
                    logger.info("流程实例[{}], 当前节点[{}]为人工审批节点", nova.getProcessInstId(), nextNode.getId());
                    nova = super.processSchedule(actuator, nova);
                    if (ProcessConstants.STATUS_APPROVAL.equals(nova.getNodeStatus())) {
                        logger.info("流程实例[{}], 当前节点[{}]状态为审批中, 请进行人工审批!", nova.getProcessInstId(), nova.getNodeId());
                        nova.setProcessStatus(ProcessConstants.STATUS_APPROVAL);
                    }
                }
            } catch (Exception e) {
                String errorMessage = Novas.formatMessage("流程实例[{}], 自动节点[{}]调度异常!", nova.getProcessInstId(), nextNode.getId());
                logger.error(errorMessage, e);
                nova.setProcessStatus(ProcessConstants.STATUS_EXCEPTION);
                throw new NovaException(errorMessage, e);
            } finally {
                Processes.updateStatus(nova.getProcessInstId(), nova.getProcessStatus());
            }
        } else {
            logger.warn("流程实例[{}], 当前节点[{}]状态为:{}, 退出本次执行", nova.getProcessInstId(), nova.getNodeId(), nova.getNodeStatus());
        }
        
        return nova;
        
    }
    
}
