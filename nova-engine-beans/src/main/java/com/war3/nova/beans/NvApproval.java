package com.war3.nova.beans;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 审批配置信息
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午10:19:15
 * @version 1.0
 */
public class NvApproval extends ToString implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 最大人员审批处理数量
	 */
	@JacksonXmlProperty(isAttribute=true)
	private int maxApprovalNum = -1;
	
	// 审批类型
	@JacksonXmlProperty(isAttribute=true)
	private String approvalType;
	
	// 审批集合类型
	@JacksonXmlProperty(isAttribute=true)
	private String sets;
	
	// 审批配置信息
	@JacksonXmlElementWrapper(localName="nv:approvers")
	@JacksonXmlProperty(localName="nv:approver")
	private List<NvApprover> approver;
	
	// 会签规则
	@JacksonXmlProperty(localName="nv:mulit")
	private NvMulti mulit;
	
	/**
	 * 审批人数
	 */
	@JacksonXmlProperty(isAttribute=true)
	private int approverNum = 1;
	
	/**
	 * 自定义的审批人员权限启用标识
	 */
	@JacksonXmlProperty(isAttribute=true)
	private boolean customizedAprvAuth = false;
	
	/**
	 * 自定义的审批人员权限执行模式
	 */
	@JacksonXmlProperty(isAttribute=true)
	private String customizedAprvAuthMode;
	
	/**
	 * 自定义的审批人员权限执行配置值
	 */
	@JacksonXmlProperty(isAttribute=true)
	private String customizedAprvAuthValue;

	public int getApproverNum() {
        return approverNum;
    }

    public void setApproverNum(int approverNum) {
        this.approverNum = approverNum;
    }

    public int getMaxApprovalNum() {
		return maxApprovalNum;
	}

	public void setMaxApprovalNum(int maxApprovalNum) {
		this.maxApprovalNum = maxApprovalNum;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getSets() {
		return sets;
	}

	public void setSets(String sets) {
		this.sets = sets;
	}

	public List<NvApprover> getApprover() {
		return approver;
	}

	public void setApprover(List<NvApprover> approver) {
		this.approver = approver;
	}
	
	public void addApprover(NvApprover approver) {
		if (this.approver == null) {
			this.approver = new CopyOnWriteArrayList<>();
		}
		this.approver.add(approver);
	}

	public NvMulti getMulit() {
		return mulit;
	}

	public void setMulit(NvMulti mulit) {
		this.mulit = mulit;
	}
	
	public boolean isCustomizedAprvAuth() {
		return customizedAprvAuth;
	}

	public void setCustomizedAprvAuth(boolean customizedAprvAuth) {
		this.customizedAprvAuth = customizedAprvAuth;
	}

	public String getCustomizedAprvAuthMode() {
		return customizedAprvAuthMode;
	}

	public void setCustomizedAprvAuthMode(String customizedAprvAuthMode) {
		this.customizedAprvAuthMode = customizedAprvAuthMode;
	}

	public String getCustomizedAprvAuthValue() {
		return customizedAprvAuthValue;
	}

	public void setCustomizedAprvAuthValue(String customizedAprvAuthValue) {
		this.customizedAprvAuthValue = customizedAprvAuthValue;
	}

}
