package com.war3.nova.core.calculator.approval.range;

import java.io.Serializable;

/**
 * 流程启动用户信息
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午5:57:17
 * @version 1.0
 */
public class ProcessStartUser implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String startUser;
    
    private String startOrgId;
    
    private String startInstOrgId;

    public ProcessStartUser(String startUser, String startOrgId, String startInstOrgId) {
        super();
        this.startUser = startUser;
        this.startOrgId = startOrgId;
        this.startInstOrgId = startInstOrgId;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getStartOrgId() {
        return startOrgId;
    }

    public void setStartOrgId(String startOrgId) {
        this.startOrgId = startOrgId;
    }

    public String getStartInstOrgId() {
        return startInstOrgId;
    }

    public void setStartInstOrgId(String startInstOrgId) {
        this.startInstOrgId = startInstOrgId;
    }
    
}