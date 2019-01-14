package com.war3.nova.core.actuator.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsTask;
import com.war3.nova.beans.NvTask;
import com.war3.nova.core.AbstractCoreActuator;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.CoreListener;
import com.war3.nova.core.actuator.TaskActuator;
import com.war3.nova.core.customized.task.TaskParameter;
import com.war3.nova.core.customized.task.TaskRunner;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.enumeration.TaskScheduleType;
import com.war3.nova.core.listener.DelegateTask;
import com.war3.nova.core.service.util.Tasks;
import com.war3.nova.core.thread.ThreadPool;
import com.war3.nova.core.util.Listeners;
import com.war3.nova.core.util.Parameters;

/**
 * 任务执行
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午10:37:37
 * @version 1.0
 */
public abstract class AbstractTaskCoreActuator extends AbstractCoreActuator<DelegateTask> implements TaskActuator {
    
    @Autowired
    @Qualifier("taskThreadPool")
    private ThreadPool threadPool;

    /**
     * 初始化任务
     * @param nova
     * @return
     */
    public NvInsTask initTask(Nova nova) {
        NvTask task = ProcessConfigContext.getContext().getCurrentTask();
        NvInsTask insTask = null;
        //约定  节点状态为异常时为流程异常调度在次进入task执行，而不是其他
        if (ProcessConstants.STATUS_EXCEPTION.equals(nova.getNodeStatus())) {
            insTask = Tasks.queryByNodeInstIdAndTaskId(nova.getNodeInstId(), nova.getTaskId());
        } else {
            insTask = Tasks.createInsTask(nova, task);
            Tasks.initInsTask(insTask);
        }
        return insTask;
    }
    
    /**
     * 更新任务状态
     * @param nodeInstanceId
     * @param taskId
     * @param status
     */
    public void updateInsTaskStatus(String nodeInstanceId, String taskId, String status, String errorMsg) {
        Tasks.updateInsTaskStatus(nodeInstanceId, taskId, status, errorMsg);
    }
    
    /**
     * 执行任务
     * @param nova
     * @throws Exception
     */
    protected void executeTask(Nova nova) throws Exception {
        NvTask task = ProcessConfigContext.getContext().getCurrentTask();
        TaskParameter parameter = Parameters.createTaskParameter(nova, task.getFields());
        ExecutionMode mode = ExecutionMode.get(task.getExecutionMode());
        
        TaskRunner taskRunnable = new TaskRunner(parameter, mode, nova.getNodeInstId(), nova.getTaskId(), task.getValue());
        if (TaskScheduleType.compare(TaskScheduleType.SYNC, task.getScheduleType())) {
            taskRunnable.run();
            Result<?> result = taskRunnable.getResult();
            if (!result.isSuccess()) {
                throw new NovaException(result.errorCode(), result.errorMessage());
            }
        } else {
            threadPool.addRunnable(taskRunnable);
        }
    }

    @Override
    public DelegateTask getListenerDelegate(Nova nova, Exception e) {
        return Listeners.createTaskDelegate(nova, e);
    }

    @SuppressWarnings("unchecked")
    public List<? extends CoreListener<DelegateTask>> getListeners() throws NovaException {
        return (List<? extends CoreListener<DelegateTask>>) Listeners.initListener(ProcessConfigContext.getContext().getCurrentTask().getListeners());
    }
    

}
