package com.war3.nova.beans;

import java.io.Serializable;

/**
 * 审批人信息
 * 
 * @author Cytus_
 * @since 2018年12月14日 下午1:18:45
 * @version 1.0
 */
public class Approver extends ToString implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 审批人名称
    private String aprvUserName;
    
    // 审批人
    private String aprvUser;
    
    // 审批人所在机构
    private String aprvOrg;
    
    // 审批人所在机构名
    private String aprvOrgName;
    
    // 审批人所在金融机构
    private String aprvInstOrg;
    
    public String getAprvInstOrg() {
        return aprvInstOrg;
    }

    public void setAprvInstOrg(String aprvInstOrg) {
        this.aprvInstOrg = aprvInstOrg;
    }

    public String getAprvUserName() {
        return aprvUserName;
    }

    public void setAprvUserName(String aprvUserName) {
        this.aprvUserName = aprvUserName;
    }

    public String getAprvUser() {
        return aprvUser;
    }

    public void setAprvUser(String aprvUser) {
        this.aprvUser = aprvUser;
    }

    public String getAprvOrg() {
        return aprvOrg;
    }

    public void setAprvOrg(String aprvOrg) {
        this.aprvOrg = aprvOrg;
    }

    public String getAprvOrgName() {
        return aprvOrgName;
    }

    public void setAprvOrgName(String aprvOrgName) {
        this.aprvOrgName = aprvOrgName;
    }
    
}
