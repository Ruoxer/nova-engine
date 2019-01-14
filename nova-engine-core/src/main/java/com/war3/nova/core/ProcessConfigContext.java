package com.war3.nova.core;

import java.util.List;

import com.war3.nova.beans.NvListener;
import com.war3.nova.beans.NvNode;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.beans.NvTask;

/**
 * 流程配置的上线文线程对象
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:32:03
 * @version 1.0
 */
public class ProcessConfigContext {
    
    /**
     * 流程配置对象信息
     */
    private NvProcess process;
    
    /**
     * 当前节点id
     */
    private String nodeId;
    
    /**
     * 当前任务id
     */
    private String taskId;
    
    private final static ThreadLocal<ProcessConfigContext> threadLocal = new ThreadLocal<>();
    
    private ProcessConfigContext(NvProcess process) {
        this.process = process;
        threadLocal.set(this);
    }
    
    public final static ProcessConfigContext createContext(NvProcess process) {
        return new ProcessConfigContext(process);
    }
    
    public static ProcessConfigContext getContext() {
        return threadLocal.get();
    }
    
    public NvProcess getProcess() {
        return this.process;
    }
    
    public List<NvListener> getNodeListener(String nodeId) {
        NvNode node = this.process.getNode().get(nodeId);
        return node.getListeners();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public NvTask getCurrentTask() {
        return getCurrentNode().getTask(taskId);
    }
    
    public NvNode getCurrentNode() {
        return process.getNode(nodeId);
    }
    
    
}
