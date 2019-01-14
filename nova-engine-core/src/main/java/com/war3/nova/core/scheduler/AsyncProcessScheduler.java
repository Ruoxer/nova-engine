package com.war3.nova.core.scheduler;

import java.util.Objects;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvNode;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.core.CoreSelector;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.actuator.ProcessActuator;
import com.war3.nova.core.enumeration.ActuatorType;
import com.war3.nova.core.enumeration.RouteType;
import com.war3.nova.core.factory.ActuatorFactory;
import com.war3.nova.core.factory.SelectorFactory;
import com.war3.nova.core.service.util.Processes;
import com.war3.nova.core.thread.DefaultThreadResult;
import com.war3.nova.core.util.Novas;

/**
 * 异步流程度调度
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午8:45:25
 * @version 1.0
 */
public class AsyncProcessScheduler extends AbstractProcessScheduler implements Callable<Result<?>> {
    
    private final static Logger logger = LoggerFactory.getLogger(AsyncProcessScheduler.class);

    private Nova nova;
    
    private NvProcess process;
    
    public AsyncProcessScheduler(Nova nova, NvProcess process) {
        this.nova = nova;
        this.nova.setProcessStatus(ProcessConstants.STATUS_RUNNING);
        this.process = process;
    }
    
    @Override
    public Nova dispatch(Nova nova) throws NovaException {

        ProcessActuator actuator = ActuatorFactory.factory().getProcessActuator(ActuatorType.PROCESS);
        CoreSelector<NvNode> routeNodeSelector = SelectorFactory.factory().getRouteSelector(RouteType.NODE); 
        while (true) {
            nova = super.processSchedule(actuator, nova);
            
            if (ProcessConstants.STATUS_END.equals(nova.getNodeStatus())) {
                NvNode nextNode = routeNodeSelector.select(nova);
                if (Objects.isNull(nextNode)) {
                    logger.info("流程实例[{}]已执行结束!", nova.getProcessInstId());
                    Processes.updateStatus(nova.getProcessInstId(), ProcessConstants.STATUS_END);
                    // 迁移至历史记录
                    toHistory(nova.getProcessInstId());
                    break;
                } else {
                    logger.info("流程实例[{}], 下一个需要执行的节点为:{}", nova.getProcessInstId(), nextNode.getId());
                    initNextNode(nova, nextNode); 
                }
                
            }
            if (ProcessConstants.STATUS_APPROVAL.equals(nova.getNodeStatus())) {
                logger.info("流程实例[{}], 当前节点[{}]状态为审批中, 请进行人工审批!", nova.getProcessInstId(), nova.getNodeId());
                nova.setProcessStatus(ProcessConstants.STATUS_APPROVAL);
                break;
            }
            
        }
        
        return nova;
    }

    @Override
    public Result<?> call() throws Exception {
        ProcessConfigContext.createContext(process);
        // 此处更新流程状态是为了处理人工那边重办的一些操作所做的冗余
        Processes.updateStatus(nova.getProcessInstId(), nova.getProcessStatus());
        try {
            logger.info("流程实例[{}]异步调度开始", nova.getProcessInstId());
            nova = dispatch(nova);
            logger.info("流程实例[{}]异步调度结束", nova.getProcessInstId());
        } catch (Exception e) {
            logger.error(Novas.formatMessage("流程实例[{}]执行异常!", nova.getProcessInstId()), e);
        } finally {
            Processes.updateStatus(nova.getProcessInstId(), nova.getProcessStatus());
        }
        return DefaultThreadResult.createSuccess(null);
    }

}
