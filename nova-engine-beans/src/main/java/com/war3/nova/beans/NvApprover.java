package com.war3.nova.beans;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 审批人配置信息
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午10:19:30
 * @version 1.0
 */
public class NvApprover extends ToString implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 审批人类型
    @JacksonXmlProperty(isAttribute=true)
    private String type;
    
    // 范围
    @JacksonXmlProperty(isAttribute=true)
    private String range;
    
    // 配置值
    @JacksonXmlProperty(isAttribute=true)
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
