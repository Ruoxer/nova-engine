package com.war3.nova.beans;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 任务配置
 * 
 * @author Cytus_
 * @since 2018年12月25日 下午1:59:45
 * @version 1.0
 */
public class NvTask extends ToString implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JacksonXmlProperty(isAttribute=true)
    private String id;
    
    @JacksonXmlProperty(isAttribute=true)
    private String name;
    
    @JacksonXmlProperty(isAttribute=true)
    private String scheduleType;
    
    @JacksonXmlProperty(isAttribute=true)
    private String executionMode;
    
    @JacksonXmlProperty(isAttribute=true)
    private int order;
    
    @JacksonXmlProperty(isAttribute=true)
    private String value;
    
    @JacksonXmlElementWrapper(localName="nv:fields")
    @JacksonXmlProperty(localName="nv:field")
    private List<NvField> fields;
    
    @JacksonXmlElementWrapper(localName="nv:listeners")
    @JacksonXmlProperty(localName="nv:listener")
    private List<NvListener> listeners;

    public List<NvListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<NvListener> listeners) {
        this.listeners = listeners;
    }
    
    public void addListeners(NvListener listener) {
        if (this.listeners == null) {
            this.listeners = new CopyOnWriteArrayList<>();
        }
        this.listeners.add(listener);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getExecutionMode() {
        return executionMode;
    }

    public void setExecutionMode(String executionMode) {
        this.executionMode = executionMode;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<NvField> getFields() {
        return fields;
    }

    public void setFields(List<NvField> fields) {
        this.fields = fields;
    }
    
    public void addFields(NvField field) {
        if (this.fields == null) {
            this.fields = new CopyOnWriteArrayList<>();
        }
        this.fields.add(field);
    }

}
