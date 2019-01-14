package com.war3.nova.beans;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


/**
 * 监听信息对象
 * 
 * @author Cytus_
 * @since 2018年12月25日 下午2:03:36
 * @version 1.0
 */
public class NvListener extends ToString implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JacksonXmlProperty(isAttribute=true)
    private String executionMode;
    
    @JacksonXmlProperty(isAttribute=true)
    private String value;

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
