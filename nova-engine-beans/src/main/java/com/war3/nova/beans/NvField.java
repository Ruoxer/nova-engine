package com.war3.nova.beans;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 自定义当前字段
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午10:19:47
 * @version 1.0
 */
public class NvField extends ToString implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 字段名
     */
    @JacksonXmlProperty(isAttribute=true)
    private String name;
    
    /**
     * 字段默认值
     */
    @JacksonXmlProperty(isAttribute=true)
    private String value;
    
    /**
     * 字段描述
     */
    @JacksonXmlProperty(isAttribute=true)
    private String decription;
    
    /**
     * 值类型
     */
    @JacksonXmlProperty(isAttribute=true)
    private String valueType;
    
    /**
     * 上下文key
     */
    @JacksonXmlProperty(isAttribute=true)
    private String contextKey;
    
    /**
     * 数据类型
     */
    @JacksonXmlProperty(isAttribute=true)
    private String dataType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getContextKey() {
        return contextKey;
    }

    public void setContextKey(String contextKey) {
        this.contextKey = contextKey;
    }

    public String getDataType() {
        return (dataType == null) ? "string" : dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

}
