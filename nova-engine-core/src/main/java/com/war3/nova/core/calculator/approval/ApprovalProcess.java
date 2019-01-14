package com.war3.nova.core.calculator.approval;

import java.io.Serializable;

/**
 * 自定义审批流程用的相关扩展对象
 * 
 * @author Cytus_
 * @since 2018年12月20日 下午5:22:38
 * @version 1.0
 */
public class ApprovalProcess implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String processId;
    
    private String nodeId;
    
    private String bizSerno;
    
    public ApprovalProcess() {
        super();
    }

    public ApprovalProcess(String processId, String nodeId, String bizSerno) {
        super();
        this.processId = processId;
        this.nodeId = nodeId;
        this.bizSerno = bizSerno;
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

    public String getBizSerno() {
        return bizSerno;
    }

    public void setBizSerno(String bizSerno) {
        this.bizSerno = bizSerno;
    }

}
