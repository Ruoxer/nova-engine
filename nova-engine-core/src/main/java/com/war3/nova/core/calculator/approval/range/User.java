package com.war3.nova.core.calculator.approval.range;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户信息对象
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午4:29:16
 * @version 1.0
 */
public class User implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String userId;
    
    private String userName;
    
    private String orgId;
    
    private String orgName;
    
    private String instOrgId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getInstOrgId() {
        return instOrgId;
    }

    public void setInstOrgId(String instOrgId) {
        this.instOrgId = instOrgId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
