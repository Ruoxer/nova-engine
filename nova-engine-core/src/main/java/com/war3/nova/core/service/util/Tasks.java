package com.war3.nova.core.service.util;

import java.util.List;

import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsTask;
import com.war3.nova.beans.NvTask;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.service.NvInsTaskService;
import com.war3.nova.core.util.Dates;
import com.war3.nova.core.util.SpringContexts;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月15日 上午10:53:22
 * @version 1.0
 */
public class Tasks {
    
    private static final NvInsTaskService insTaskService = SpringContexts.getBean(NvInsTaskService.class);
    
    public final static void initInsTask(NvInsTask insTask) {
        insTaskService.insert(insTask);
    }
    
    public final static NvInsTask createInsTask(Nova nova, NvTask task) {
        NvInsTask insTask = new NvInsTask();
        insTask.setStartTime(Dates.formatDateTimeByDef());
        insTask.setExecutionMode(task.getExecutionMode());
        insTask.setNodeInstanceId(nova.getNodeInstId());
        insTask.setOrder(task.getOrder());
        insTask.setScheduleType(task.getScheduleType());
        insTask.setStatus(nova.getTaskStatus());
        insTask.setTaskId(task.getId());
        insTask.setTaskName(task.getName());
        insTask.setTaskValue(task.getValue());
        if (ProcessConstants.STATUS_END.equals(insTask.getStatus())) {
            insTask.setEndTime(Dates.formatDateTimeByDef());
        }
        insTask.setProcessInstId(nova.getProcessInstId());
        return insTask;
    }
    
    public final static void updateInsTaskStatus(String nodeInstanceId, String taskId, String status, String errorMsg) {
        NvInsTask insTask = new NvInsTask();
        insTask.setNodeInstanceId(nodeInstanceId);
        insTask.setTaskId(taskId);
        insTask.setStatus(status);
        if (ProcessConstants.STATUS_END.equals(status)) { 
            insTask.setEndTime(Dates.formatDateTimeByDef());
        }
        
        if (ProcessConstants.STATUS_EXCEPTION.equals(status)) {
        	insTask.setErrorMsg(errorMsg);
        }
        
        insTaskService.update(insTask);
    }
    
    public final static List<NvInsTask> queryAllByNodeInstanceId(String nodeInstanceId) {
        return insTaskService.queryAllByNodeInstId(nodeInstanceId);
    }

    
    public final static NvInsTask queryByNodeInstIdAndTaskId(String nodeInstId, String taskId) {
    	return insTaskService.queryByNodeInstIdAndTaskId(nodeInstId, taskId);
    }
    
}
