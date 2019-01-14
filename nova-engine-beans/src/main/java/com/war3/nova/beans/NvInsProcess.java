package com.war3.nova.beans;

import java.io.Serializable;


/**
 * 实例审批信息
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午10:20:41
 * @version 1.0
 */
public class NvInsProcess extends ToString implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String processId;
    
    private String instanceId;
    
    private String mainInstanceId;
    
    private String bizSerno;
    
    private String status;
    
    private String nodeInstanceId;
    
    private String orgId;
    
    private String instOrgId;
    
    private String startUser;
    
    private String startTime;
    
    private String endTime;
    
    private String version;
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNodeInstanceId() {
        return nodeInstanceId;
    }

    public void setNodeInstanceId(String nodeInstanceId) {
        this.nodeInstanceId = nodeInstanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getBizSerno() {
        return bizSerno;
    }

    public void setBizSerno(String bizSerno) {
        this.bizSerno = bizSerno;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getMainInstanceId() {
        return mainInstanceId;
    }

    public void setMainInstanceId(String mainInstanceId) {
        this.mainInstanceId = mainInstanceId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getInstOrgId() {
        return instOrgId;
    }

    public void setInstOrgId(String instOrgId) {
        this.instOrgId = instOrgId;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
