package com.war3.nova.core.listener;

import com.war3.nova.core.enumeration.NodeType;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月28日 上午11:27:19
 * @version 1.0
 */
public class DelegateNode extends Delegate {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String processId;
    
    private String nodeId;
    
    private String nodeName;
    
    private NodeType nodeType;
    
    private String nodeStatus;
    
    private String nextNodeId;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }
}
