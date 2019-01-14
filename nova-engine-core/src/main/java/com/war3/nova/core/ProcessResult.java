package com.war3.nova.core;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.war3.nova.beans.Approver;

/**
 * 流程操作结果集
 * 
 * @author Cytus_
 * @since 2018年12月21日 上午11:36:20
 * @version 1.0
 */
public class ProcessResult implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String processInstId;
    
    private String nodeInstId;
    
    private String nextNodeId;
    
    private List<Approver> nextApprovers;
    
    private String nextScheduleMode;
    
    private boolean result;
    
    protected boolean isSuccess;
    
    protected String errorCode;
    
    protected String errorMessage;
    
    public ProcessResult() {
        super();
    }
    
    public ProcessResult(boolean isSuccess, String errorCode, String errorMessage) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public ProcessResult(boolean result, boolean isSuccess, String errorCode, String errorMessage) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.result = result;
    }
    
    public final static ProcessResult success() {
        return new ProcessResult(true, true, null, null);
    }
    
    public final static ProcessResult failure(String errorCode, String errorMsg) {
        return new ProcessResult(false, false, errorCode, errorMsg);
    }

    public Boolean getResult() {
        return this.result;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }
    
    public List<Approver> getNextApprovers() {
        return nextApprovers;
    }

    public void setNextApprovers(List<Approver> nextApprovers) {
        this.nextApprovers = nextApprovers;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getNextScheduleMode() {
        return nextScheduleMode;
    }

    public void setNextScheduleMode(String nextScheduleMode) {
        this.nextScheduleMode = nextScheduleMode;
    }
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
