package com.war3.nova.core.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvNode;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.beans.NvRoute;
import com.war3.nova.core.CoreScheduler;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.actuator.NodeActuator;
import com.war3.nova.core.actuator.ProcessActuator;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.factory.ActuatorFactory;
import com.war3.nova.core.service.util.Historys;
import com.war3.nova.core.util.Novas;

/**
 * 抽象类流程调度器
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午8:39:58
 * @version 1.0
 */
public abstract class AbstractProcessScheduler implements CoreScheduler {
    
    private final static Logger logger = LoggerFactory.getLogger(AbstractProcessScheduler.class);
    
    protected void initNextNode(Nova nova, NvNode nextNode) {
        nova.setPreNodeId(nova.getNodeId());
        nova.setNodeId(nextNode.getId());
        nova.setNodeStatus(null);
        nova.setTaskId(null);
        nova.setPreTaskId(null);
        nova.setTaskStatus(null);
        nova.setNodeInstId(null);
    }
    
    /**
     * 执行开始节点调度
     * @param nova
     * @param startNode
     * @throws NovaException
     */
    protected void doStartNodeScheduler(Nova nova, NvNode startNode) throws NovaException {
        Nova snova = new Nova();
        snova.setBizSerno(nova.getBizSerno());
        snova.setProcessId(nova.getProcessId());
        snova.setProcessInstId(nova.getProcessInstId());
        snova.setMainProcInstId(nova.getMainProcInstId());
        snova.setNodeId(startNode.getId());
        NodeActuator nodeActuator = ActuatorFactory.factory().getNodeActuator(NodeType.START);
        nodeActuator.execute(snova);
    }

    /**
     * 计算开始节点
     * @param process
     * @param nodeId
     * @return
     */
    protected NvNode getPreStartNode(NvProcess process, String nodeId) {
        
        Map<String, List<NvRoute>> routeMaps = process.getRoute();
        
        List<NvRoute> routeLists = routeMaps.values().parallelStream()
                .reduce(new LinkedList<NvRoute>(), (a, b) -> {a.addAll(b); return a;});
        List<NvNode> startNode = routeLists.parallelStream()
                .filter(s -> s.getTargetRef().equals(nodeId))
                .map(s -> process.getNode(s.getSourceRef()))
                .filter(s -> NodeType.compare(NodeType.START, s.getType()))
                .collect(Collectors.toList());
        return Novas.nonNullOrEmpty(startNode) ? startNode.get(0) : null;
    }
    
    /**
     * 流程调度
     * @param actuator
     * @param nova
     * @return
     * @throws NovaException
     */
    protected Nova processSchedule(ProcessActuator actuator, Nova nova) throws NovaException {
        
        logger.info("流程[{}], 流程实例[{}], 节点[{}]调用开始", nova.getProcessId(), nova.getProcessInstId(), nova.getNodeId());
        ProcessConfigContext.getContext().setNodeId(nova.getNodeId());
        ProcessConfigContext.getContext().setTaskId(null);
        nova = actuator.execute(nova);
        logger.info("流程[{}], 流程实例[{}], 节点[{}]调用结束", nova.getProcessId(), nova.getProcessInstId(), nova.getNodeId());
        
        return nova;
        
    }
    
    /**
     * 历史迁移
     * @param processInstId
     */
    protected void toHistory(String processInstId) {
        logger.info("流程实例[{}]实例信息迁移开始", processInstId);
        Historys.toHistory(processInstId);
        logger.info("流程实例[{}]实例信息迁移结束", processInstId);
    }

}
