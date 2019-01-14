package com.war3.nova.beans;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 流程/节点属性
 * 
 * @author Cytus_
 * @since 2018年12月29日 下午4:31:57
 * @version 1.0
 */
public class NvProperty implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 是否可催办
     */
    @JacksonXmlProperty(isAttribute=true)
    private String urge;
    
    /**
     * 是否可转办
     */
    @JacksonXmlProperty(isAttribute=true)
    private String transfer;
    
    /**
     * 是否可强制结束
     */
    @JacksonXmlProperty(isAttribute=true)
    private String forcedEnd;
    
    /**
     * 是否可退回
     */
    @JacksonXmlProperty(isAttribute=true)
    private String returnBack;
    
    /**
     * 是否可打回
     */
    @JacksonXmlProperty(isAttribute=true)
    private String callBack;
    
    /**
     * 是否可拿回
     */
    @JacksonXmlProperty(isAttribute=true)
    private String takeBack;
    
    /**
     * 是否可取消
     */
    @JacksonXmlProperty(isAttribute=true)
    private String cancel;
    
    /**
     * 是否可挂起
     */
    @JacksonXmlProperty(isAttribute=true)
    private String hang;
    
    /**
     * 是否可唤醒
     */
    @JacksonXmlProperty(isAttribute=true)
    private String wake;
    
    /**
     * 是否可重新审批
     */
    @JacksonXmlProperty(isAttribute=true)
    private String reApproval;

    
    /**
     * 是否可退回审批池
     */
    @JacksonXmlProperty(isAttribute=true)
    private String returnAprvPool;
    
    /**
     * 挂载审批业务页面是否可修改
     */
    @JacksonXmlProperty(isAttribute=true)
    private String pageModify;

    public String getUrge() {
        return urge;
    }

    public void setUrge(String urge) {
        this.urge = urge;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getForcedEnd() {
        return forcedEnd;
    }

    public void setForcedEnd(String forcedEnd) {
        this.forcedEnd = forcedEnd;
    }

    public String getReturnBack() {
        return returnBack;
    }

    public void setReturnBack(String returnBack) {
        this.returnBack = returnBack;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String getTakeBack() {
        return takeBack;
    }

    public void setTakeBack(String takeBack) {
        this.takeBack = takeBack;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getWake() {
        return wake;
    }

    public void setWake(String wake) {
        this.wake = wake;
    }

    public String getReApproval() {
        return reApproval;
    }

    public void setReApproval(String reApproval) {
        this.reApproval = reApproval;
    }

    public String getReturnAprvPool() {
        return returnAprvPool;
    }

    public void setReturnAprvPool(String returnAprvPool) {
        this.returnAprvPool = returnAprvPool;
    }

    public String getPageModify() {
        return pageModify;
    }

    public void setPageModify(String pageModify) {
        this.pageModify = pageModify;
    }
    
}
