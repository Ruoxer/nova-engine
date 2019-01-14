package com.war3.nova.core.customized.route;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.war3.nova.beans.NvInsApproval;

/**
 * 路由参数
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:43:58
 * @version 1.0
 */
public class RouteParameter implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String bizSerno;
    
    private Map<String, Object> extParams;
    
    private List<NvInsApproval> approvals;
    
    private String nodeId;
    
    private String processId;
    
    public String getBizSerno() {
        return bizSerno;
    }

    public void setBizSerno(String bizSerno) {
        this.bizSerno = bizSerno;
    }

    public Map<String, Object> getExtParams() {
        return extParams;
    }

    public void setExtParams(Map<String, Object> extParams) {
        this.extParams = extParams;
    }
    
    public List<NvInsApproval> getApprovals() {
        return approvals;
    }

    public void setApprovals(List<NvInsApproval> approvals) {
        this.approvals = approvals;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

}

