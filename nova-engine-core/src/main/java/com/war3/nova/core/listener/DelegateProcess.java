package com.war3.nova.core.listener;

/**
 * 流程
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午9:30:09
 * @version 1.0
 */
public class DelegateProcess extends Delegate {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String processId;
    
    private String processName;
    
    private String processStatus;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
    
}
