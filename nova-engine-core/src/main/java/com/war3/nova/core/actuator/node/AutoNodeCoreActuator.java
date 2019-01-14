package com.war3.nova.core.actuator.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.CoreSelector;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.actuator.TaskActuator;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.enumeration.RouteType;
import com.war3.nova.core.enumeration.TaskType;
import com.war3.nova.core.factory.ActuatorFactory;
import com.war3.nova.core.factory.SelectorFactory;
import com.war3.nova.core.service.util.Approvals;
import com.war3.nova.core.util.Asserts;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Strings;

/**
 * 自动节点执行器
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:35:43
 * @version 1.0
 */
@NovaMapper(enumValue = "AUTO", enumClass = NodeType.class, mapperName = Constants.NODE_ACTUATOR)
public class AutoNodeCoreActuator extends AbstractNodeCoreActuator {
    
    private final static Logger logger = LoggerFactory.getLogger(AutoNodeCoreActuator.class);

    @Override
    public Nova doExecute(Nova nova) throws NovaException {
        
        String errorMsg = null;
        
        NvInsNode insNode = this.initAndGetInstance(nova);
        
        Asserts.assertNull(insNode, "流程实例[{}], 节点[{}]未获得实例化信息!", nova.getProcessInstId(), nova.getNodeId());
        nova.setNodeInstId(insNode.getInstanceId());
        
        CoreSelector<String> taskRouteSelector = SelectorFactory.factory().getRouteSelector(RouteType.TASK);
        String taskId = taskRouteSelector.select(nova);
        if (Strings.isEmpty(taskId)) {
            logger.warn("流程实例[{}], 节点实例[{}], 节点[{}]未找到需要执行的节点下任务!", nova.getProcessInstId(), nova.getNodeInstId(), nova.getNodeId());
            return nova;
        } 
        
        logger.info("流程实例[{}], 节点实例[{}], 待执行任务为[{}]", nova.getProcessInstId(), nova.getNodeInstId(), taskId);
        
        try {
            TaskActuator actuator = ActuatorFactory.factory().getTaskActuator(TaskType.DEFAULT);
            while (true) {
                
                nova.setTaskId(taskId);
                ProcessConfigContext.getContext().setTaskId(taskId);
                logger.info("流程实例[{}], 节点实例[{}], 执行任务[{}]开始", nova.getProcessInstId(), nova.getNodeInstId(), nova.getTaskId());
                nova = actuator.execute(nova);
                nova.setNodeStatus(ProcessConstants.STATUS_RUNNING);
                logger.info("流程实例[{}], 节点实例[{}], 执行任务[{}]结束, 任务执行结果状态为[{}]", nova.getProcessInstId(), nova.getNodeInstId(), nova.getTaskId(), nova.getTaskStatus());
                
                if (Novas.exists(nova.getTaskStatus(), /*ProcessConstants.STATUS_PAUSE,*/ ProcessConstants.STATUS_EXCEPTION)) {
                    nova.setNodeStatus(nova.getTaskStatus());
                    break;
                }
                
                String nextTaskId = taskRouteSelector.select(nova);
                if (Strings.isEmpty(nextTaskId)) {
                    logger.info("流程实例[{}], 节点实例[{}], 节点[{}]下无可执行任务, 当前节点执行结束!", nova.getProcessInstId(), nova.getNodeInstId(), nova.getNodeId());
                    nova.setNodeStatus(ProcessConstants.STATUS_END);
                    break;
                }
                taskId = nextTaskId;
                logger.info("流程实例[{}], 节点实例[{}], 节点[{}], 下一执行任务为[{}]", nova.getProcessInstId(), nova.getNodeInstId(), nova.getNodeId(), taskId);
            }
            Approvals.initSystemAutoApproval(nova.getProcessInstId(), nova.getNodeInstId(), "1", "系统默认通过!");
        } catch (NovaException e) {
            nova.setNodeStatus(ProcessConstants.STATUS_EXCEPTION);
            logger.error(Novas.formatMessage("流程实例[{}], 节点实例[{}], 执行任务[{}]出现异常! cause by:{}", nova.getProcessInstId(), nova.getNodeInstId(), nova.getNodeId(), e.getMessage()));
            errorMsg = Novas.formatMessage("任务[{}]执行异常! Cause by:{}", nova.getTaskId(), e.getMessage());
            throw e;
        } catch (Exception e) {
            nova.setNodeStatus(ProcessConstants.STATUS_EXCEPTION);
            logger.error(Novas.formatMessage("流程实例[{}], 节点实例[{}], 执行任务[{}]出现异常! cause by:{}", nova.getProcessInstId(), nova.getNodeInstId(), nova.getNodeId(), e.getMessage()));
            errorMsg = Novas.formatMessage("任务[{}]执行异常! Cause by:{}", nova.getTaskId(), e.getMessage());
            throw new NovaException(e);
        } finally {
            super.updateNodeStatus(nova.getNodeInstId(), nova.getNodeStatus(), errorMsg);
        }
        
        return nova;
    }
    

}
