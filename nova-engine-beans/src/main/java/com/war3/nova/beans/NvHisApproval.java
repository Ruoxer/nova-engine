package com.war3.nova.beans;

/**
 * 审批历史信息
 * 
 * @author Cytus_
 * @since 2018年12月14日 下午1:18:57
 * @version 1.0
 */
public class NvHisApproval extends Approver {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String approvalId;
    
    private String nodeInstanceId;
    
    private String aprvResult;
    
    private String aprvComment;
    
    private String aprvTime;

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


}
