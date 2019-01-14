package com.war3.nova.beans;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 会签规则
 * 
 * @author Cytus_
 * @since 2018年12月25日 下午2:04:04
 * @version 1.0
 */
public class NvMulti extends ToString implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JacksonXmlProperty(isAttribute=true)
    private String ruleType;
    
    @JacksonXmlProperty(isAttribute=true)
    private String executionMode;
    
    @JacksonXmlProperty(isAttribute=true)
    private String value;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getExecutionMode() {
        return executionMode;
    }

    public void setExecutionMode(String executionMode) {
        this.executionMode = executionMode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
