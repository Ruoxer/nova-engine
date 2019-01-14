package com.war3.nova.beans;

/**
 * 审批信息
 * 
 * @author Cytus_
 * @since 2018年12月14日 下午1:18:57
 * @version 1.0
 */
public class NvInsApproval extends Approver {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String approvalId;
    
    private String nodeInstanceId;
    
    /**
     * 是否已提交
     */
    private String submitted;
    
    private String aprvResult;
    
    private String aprvComment;
    
    private String aprvTime;
    
    private String processInstId;

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getAprvTime() {
        return aprvTime;
    }

    public void setAprvTime(String aprvTime) {
        this.aprvTime = aprvTime;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getNodeInstanceId() {
        return nodeInstanceId;
    }

    public void setNodeInstanceId(String nodeInstanceId) {
        this.nodeInstanceId = nodeInstanceId;
    }

    public String getAprvResult() {
        return aprvResult;
    }

    public void setAprvResult(String aprvResult) {
        this.aprvResult = aprvResult;
    }

    public String getAprvComment() {
        return aprvComment;
    }

    public void setAprvComment(String aprvComment) {
        this.aprvComment = aprvComment;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }


}
