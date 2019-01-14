package com.war3.nova.core.listener;

/**
 * 任务监听对象
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:50:51
 * @version 1.0
 */
public class DelegateTask extends Delegate {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String taskId;
    
    private String taskName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
