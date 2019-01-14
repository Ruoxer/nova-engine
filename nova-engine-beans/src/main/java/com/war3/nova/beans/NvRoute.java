package com.war3.nova.beans;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 路由信息
 * 
 * @author Cytus_
 * @since 2018年12月25日 下午2:05:27
 * @version 1.0
 */
public class NvRoute extends ToString implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JacksonXmlProperty(isAttribute=true)
    private String id;

    @JacksonXmlProperty(isAttribute=true)
    private String name;
    
    @JacksonXmlProperty(isAttribute=true)
    private String type;
    
    @JacksonXmlProperty(isAttribute=true)
    private String sourceRef;
    
    @JacksonXmlProperty(isAttribute=true)
    private String targetRef;
    
    @JacksonXmlProperty(isAttribute=true)
    private String description;

    @JacksonXmlProperty(isAttribute=true)
    private String conditionExecutionMode;
    
    @JacksonXmlProperty(isAttribute=true)
    private String condition;
    
    @JacksonXmlElementWrapper(localName="nv:fields")
    @JacksonXmlProperty(localName="nv:field")
    private List<NvField> fields;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditionExecutionMode() {
        return conditionExecutionMode;
    }

    public void setConditionExecutionMode(String conditionExecutionMode) {
        this.conditionExecutionMode = conditionExecutionMode;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
