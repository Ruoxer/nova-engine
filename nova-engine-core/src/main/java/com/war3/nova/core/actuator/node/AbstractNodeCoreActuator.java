package com.war3.nova.core.actuator.node;

import java.util.List;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsNode;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.AbstractCoreActuator;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.CoreListener;
import com.war3.nova.core.actuator.NodeActuator;
import com.war3.nova.core.listener.DelegateNode;
import com.war3.nova.core.service.util.Nodes;
import com.war3.nova.core.service.util.Processes;
import com.war3.nova.core.util.Dates;
import com.war3.nova.core.util.Listeners;
import com.war3.nova.core.util.Strings;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月15日 下午3:22:51
 * @version 1.0
 */
public abstract class AbstractNodeCoreActuator extends AbstractCoreActuator<DelegateNode> implements NodeActuator {
    
    /**
     * 初始化或查询
     * @param nova
     * @return
     */
    protected NvInsNode initAndGetInstance(Nova nova) {
        NvNode node = ProcessConfigContext.getContext().getProcess().getNode(nova.getNodeId());
        NvInsNode insNode = null;
        if (Strings.isEmpty(nova.getNodeInstId())) {
            insNode = new NvInsNode();
            insNode.setStartTime(Dates.formatDateTimeByDef());
            insNode.setInstanceId(Strings.getSequence());
            insNode.setNodeType(node.getType());
            insNode.setNodeId(node.getId());
            insNode.setNodeName(node.getName());
            insNode.setProcessInstanceId(nova.getProcessInstId());
            insNode.setStatus(nova.getNodeStatus());
            Nodes.initNode(insNode);
        } else {
            insNode = Nodes.getByInstaceId(nova.getNodeInstId());
        }
        Processes.updateCurrentNodeInstId(nova.getProcessInstId(), insNode.getInstanceId());
        return insNode;
    }

    /**
     * 获取节点监听对象
     */
    @Override
    public DelegateNode getListenerDelegate(Nova nova, Exception e) {
        return Listeners.createNodeDelegate(nova, e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<? extends CoreListener<DelegateNode>> getListeners() throws NovaException {
        return (List<? extends CoreListener<DelegateNode>>) Listeners.initListener(ProcessConfigContext.getContext().getCurrentNode().getListeners());
    }

    /**
     * 更新节点状态
     * @param nodeInstanceId
     * @param nodeStatus
     */
    public void updateNodeStatus(String nodeInstanceId, String nodeStatus, String errorMsg) {
        Nodes.updateNodeStatus(nodeInstanceId, nodeStatus, errorMsg);
    }
    
    
    
}
