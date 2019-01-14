package com.war3.nova.core.actuator.node.artificialapproval;

import java.io.Serializable;
import java.util.List;

import com.war3.nova.beans.NvInsApproval;

/**
 * 多人会签参数对象
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午5:50:36
 * @version 1.0
 */
public class MultiJoinSignParameter implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String processId;
    
    private String processInstId;
    
    private String nodeId;
    
    private String nodeInstId;
    
    private List<NvInsApproval> approvals;

    public MultiJoinSignParameter() {
        super();
    }

    public MultiJoinSignParameter(String processId, String processInstId, String nodeId, String nodeInstId,
            List<NvInsApproval> approvals) {
        super();
        this.processId = processId;
        this.processInstId = processInstId;
        this.nodeId = nodeId;
        this.nodeInstId = nodeInstId;
        this.approvals = approvals;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public List<NvInsApproval> getApprovals() {
        return approvals;
    }

    public void setApprovals(List<NvInsApproval> approvals) {
        this.approvals = approvals;
    }

}
