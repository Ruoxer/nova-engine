package com.war3.nova.core.calculator.approval.range;

import java.io.Serializable;

/**
 * 自定义审批人计算向下传递对象
 * 
 * @author Cytus_
 * @since 2018年12月28日 上午10:53:16
 * @version 1.0
 */
public class ApprovalRangeParameter implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String processId;
    
    private String nodeId;
    
    private ProcessStartUser startUser;

    public ApprovalRangeParameter() {
        super();
    }
    
    public ApprovalRangeParameter(String processId, String nodeId, ProcessStartUser startUser) {
        super();
        this.processId = processId;
        this.nodeId = nodeId;
        this.startUser = startUser;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public ProcessStartUser getStartUser() {
        return startUser;
    }

    public void setStartUser(ProcessStartUser startUser) {
        this.startUser = startUser;
    }

}
