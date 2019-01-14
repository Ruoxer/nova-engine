package com.war3.nova.core.customized.task;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.customized.ObjectResult;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.factory.RunnerFactory;
import com.war3.nova.core.service.util.Tasks;
import com.war3.nova.core.util.Novas;

/**
 * 任务执行
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午10:42:48
 * @version 1.0
 */
public class TaskRunner implements Runnable {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskRunner.class);
    
    private TaskParameter taskParameter;
    
    private ExecutionMode executionMode;
    
    private String value;
    
    private String nodeInstId;
    
    private String taskId;
    
    private Result<?> result;
    
    private String taskStatus = ProcessConstants.STATUS_RUNNING;

    public TaskRunner(TaskParameter taskParameter, ExecutionMode executionMode, String nodeInstId, String taskId, String value) {
        super();
        this.taskParameter = taskParameter;
        this.executionMode = executionMode;
        this.value = value;
        this.nodeInstId = nodeInstId;
        this.taskId = taskId;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        String errorMsg = null;
        try {
            logger.debug("节点实例[{}], 任务编号[{}]执行开始", nodeInstId, taskId);
            ObjectActuator<TaskParameter, Result<?>> runner = (ObjectActuator<TaskParameter, Result<?>>) RunnerFactory.factory().getObjectActuator(executionMode);
            Result<?> runnerResult = runner.run(value, taskParameter);
            if (!runnerResult.isSuccess()) {
                throw new NovaException(runnerResult.errorCode(), runnerResult.errorMessage());
            }
            result = runnerResult;
            taskStatus = ProcessConstants.STATUS_END;
            logger.debug("节点实例[{}], 任务编号[{}]执行结束", nodeInstId, taskId);
        } catch (NovaException e) { 
            logger.error(Novas.formatMessage("节点实例号[{}], 任务编号[{}]执行当前任务出现异常!", nodeInstId, taskId), e);
            errorMsg = e.getMessage();
            taskStatus = ProcessConstants.STATUS_EXCEPTION;
            result = ObjectResult.createFailure(e.getErrorCode(), errorMsg);
        } catch (Exception e) {
            logger.error(Novas.formatMessage("节点实例号[{}], 任务编号[{}]执行当前任务出现异常!", nodeInstId, taskId), e);
            taskStatus = ProcessConstants.STATUS_EXCEPTION;
            errorMsg = e.getMessage();
            result = ObjectResult.createFailure(Constants.SYSTEM_ERROR_CODE, errorMsg);
        } finally {
            
            if (Objects.isNull(ProcessConfigContext.getContext())) {
                Tasks.updateInsTaskStatus(nodeInstId, taskId, taskStatus, errorMsg);
            }
            
        }
    }
    
    public Result<?> getResult() {
        return result;
    }

}
