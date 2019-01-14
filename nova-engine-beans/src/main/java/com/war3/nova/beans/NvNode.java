package com.war3.nova.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 节点信息
 * 
 * @author Cytus_
 * @since 2018年12月25日 下午2:04:34
 * @version 1.0
 */
public class NvNode extends ToString implements Serializable {
    
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
    private String description;
    
    @JacksonXmlProperty(localName="nv:task")
    private Map<String, NvTask> task;
    
    @JacksonXmlProperty(localName="nv:approval")
    private NvApproval approval;
    
    @JacksonXmlProperty(localName="nv:viewPage")
    private NvViewPage viewPage;
    
    @JacksonXmlElementWrapper(localName="nv:listeners")
    @JacksonXmlProperty(localName="nv:listener")
    private List<NvListener> listeners;
    
    @JacksonXmlProperty(localName="nv:property")
    private NvProperty property;

    public Map<String, NvTask> getTask() {
        return task;
    }
    
    public NvTask getTask(String taskId) {
        if (this.task == null) {
            this.task = new ConcurrentHashMap<>();
        }
        return task.getOrDefault(taskId, null);
    }

    public void setTask(Map<String, NvTask> task) {
        this.task = task;
    }
    
    public void addTask(NvTask task) {
        if (this.task == null) {
            this.task = new ConcurrentHashMap<>();
        }
        this.task.put(task.getId(), task);
    }

    public NvApproval getApproval() {
        return approval;
    }

    public void setApproval(NvApproval approval) {
        this.approval = approval;
    }

    public NvViewPage getViewPage() {
        return viewPage;
    }

    public void setViewPage(NvViewPage viewPage) {
        this.viewPage = viewPage;
    }

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

    public NvProperty getProperty() {
        return property;
    }

    public void setProperty(NvProperty property) {
        this.property = property;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
