package com.war3.nova.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 流程配置
 * 
 * @author Cytus_
 * @since 2018年12月25日 下午2:00:49
 * @version 1.0
 */
@JacksonXmlRootElement(localName="nv:process")
public class NvProcess extends ToString implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @JacksonXmlProperty(isAttribute=true)
    private String id;
    
    @JacksonXmlProperty(isAttribute=true)
    private String name;
    
    @JacksonXmlProperty(isAttribute=true)
    private String status;
    
    @JacksonXmlProperty(isAttribute=true)
    private String version;
    
    @JacksonXmlProperty(isAttribute=true)
    private String description;
    
    @JacksonXmlProperty(isAttribute=true)
    private String createTime;
    
    @JacksonXmlProperty(isAttribute=true)
    private String createUser;
    
    @JacksonXmlProperty(isAttribute=true)
    private String createOrg;
    
    @JacksonXmlProperty(isAttribute=true)
    private String updateTime;
    
    @JacksonXmlProperty(isAttribute=true)
    private String updateUser;
    
    @JacksonXmlProperty(isAttribute=true)
    private String updateOrg;
    
    @JacksonXmlProperty(isAttribute=true)
    private String org;
    
    @JacksonXmlProperty(isAttribute=true)
    private String instOrg;
    
    @JacksonXmlProperty(isAttribute=true)
    private String sysId;
    
    @JacksonXmlProperty(localName="nv:node")
    @JacksonXmlElementWrapper(localName="nv:node")
    private Map<String, NvNode> node;
    
    @JacksonXmlProperty(localName="nv:route")
    @JacksonXmlElementWrapper(localName="nv:route")
    private Map<String, List<NvRoute>> route;
    
    @JacksonXmlElementWrapper(localName="nv:listeners")
    @JacksonXmlProperty(localName="nv:listener")
    private List<NvListener> listeners;
    
    @JacksonXmlProperty(localName="nv:property")
    private NvProperty property;
    
    @JacksonXmlElementWrapper(localName="nv:fields")
    @JacksonXmlProperty(localName="nv:field")
    private List<NvField> fields;
    
    @JacksonXmlProperty(localName="nv:viewPage")
    private NvViewPage viewPage;

    public Map<String, NvNode> getNode() {
        return node;
    }
    
    public NvNode getNode(String nodeId) {
        return node.getOrDefault(nodeId, null);
    }

    public void setNode(Map<String, NvNode> node) {
        this.node = node;
    }
    
    public void addNode(NvNode node) {
        if (this.node == null) {
            this.node = new ConcurrentHashMap<String, NvNode>();
        }
        this.node.put(node.getId(), node);
    }

    public Map<String, List<NvRoute>> getRoute() {
        return route;
    }
    
    public List<NvRoute> getRoute(String sourceRef) {
        if (this.route == null) {
            this.route = new ConcurrentHashMap<>();
        }
        return this.route.get(sourceRef);
    }

    public void setRoute(Map<String, List<NvRoute>> route) {
        this.route = route;
    }
    
    public void addRoute(List<NvRoute> route) {
        if (this.route == null) {
            this.route = new ConcurrentHashMap<>();
        }
        if (route == null || route.isEmpty()) return;
        
        for (int i = 0; i < route.size(); i++) {
            NvRoute nvRoute = route.get(i);
            List<NvRoute> l = this.route.getOrDefault(nvRoute.getSourceRef(), new CopyOnWriteArrayList<>());
            l.add(nvRoute);
            this.route.put(nvRoute.getSourceRef(), l);
        }
    }
    
    public void addRoute(NvRoute route) {
        if (this.route == null) {
            this.route = new ConcurrentHashMap<>();
        }
        List<NvRoute> l = this.route.getOrDefault(route.getSourceRef(), new CopyOnWriteArrayList<>());
        l.add(route);
        this.route.put(route.getSourceRef(), l);
    }

    public List<NvListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<NvListener> listeners) {
        this.listeners = listeners;
    }

    public void addListeners(NvListener listeners) {
        if (this.listeners == null) {
            this.listeners = new CopyOnWriteArrayList<>();
        }
        this.listeners.add(listeners);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateOrg() {
        return createOrg;
    }

    public List<NvField> getFields() {
        return fields;
    }

    public void setFields(List<NvField> fields) {
        this.fields = fields;
    }

    public void setCreateOrg(String createOrg) {
        this.createOrg = createOrg;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateOrg() {
        return updateOrg;
    }

    public void setUpdateOrg(String updateOrg) {
        this.updateOrg = updateOrg;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getInstOrg() {
        return instOrg;
    }

    public void setInstOrg(String instOrg) {
        this.instOrg = instOrg;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public NvViewPage getViewPage() {
        return viewPage;
    }

    public void setViewPage(NvViewPage viewPage) {
        this.viewPage = viewPage;
    }

}
