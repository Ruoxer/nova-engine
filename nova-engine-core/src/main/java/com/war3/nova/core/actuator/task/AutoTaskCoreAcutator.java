package com.war3.nova.core.actuator.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Nova;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.actuator.TaskActuator;
import com.war3.nova.core.enumeration.TaskType;

/**
 * 自动任务执行
 * 
 * @author Cytus_
 * @since 2018年12月15日 上午11:42:52
 * @version 1.0
 */
@NovaMapper(enumValue = "DEFAULT", enumClass = TaskType.class, mapperName = Constants.TASK_ACTUATOR)
public class AutoTaskCoreAcutator extends AbstractTaskCoreActuator implements TaskActuator {
    
    private final static Logger logger = LoggerFactory.getLogger(AutoTaskCoreAcutator.class);

    @Override
    public Nova doExecute(Nova nova) throws NovaException {
        
        logger.info("节点实例[{}], 任务编号[{}]执行开始", nova.getNodeInstId(), nova.getTaskId());
        nova.setTaskStatus(ProcessConstants.STATUS_RUNNING);
        super.initTask(nova);
        
        String errorMsg = null;
        
        try {
            super.executeTask(nova);
            nova.setTaskStatus(ProcessConstants.STATUS_END);
        } catch (NovaException e) { 
            logger.error("节点实例号[{}], 任务编号[{}]执行当前任务出现异常! cause by:{}", nova.getNodeInstId(), nova.getTaskId(), e.getMessage());
            nova.setTaskStatus(ProcessConstants.STATUS_EXCEPTION);
            errorMsg = e.getMessage();
            throw e;
        } catch (Exception e) {
            logger.error("节点实例号[{}], 任务编号[{}]执行当前任务出现异常! cause by:{}", nova.getNodeInstId(), nova.getTaskId(), e);
            nova.setTaskStatus(ProcessConstants.STATUS_EXCEPTION);
            errorMsg = e.getMessage();
            throw new NovaException(e);
        } finally {
            super.updateInsTaskStatus(nova.getNodeInstId(), nova.getTaskId(), nova.getTaskStatus(), errorMsg);
        }
        logger.info("节点实例[{}], 任务编号[{}]执行结束", nova.getNodeInstId(), nova.getTaskId());
        return nova;
    }

}
