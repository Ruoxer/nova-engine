package com.war3.nova.core.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.LoggerFactory;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvListener;
import com.war3.nova.beans.NvNode;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.beans.NvTask;
import com.war3.nova.core.CoreListener;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.factory.ObjectFactory;
import com.war3.nova.core.listener.DelegateNode;
import com.war3.nova.core.listener.DelegateProcess;
import com.war3.nova.core.listener.DelegateTask;

/**
 * 监听器工具
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午1:01:17
 * @version 1.0
 */
public final class Listeners {
    
    /**
     * 创建监听器
     * @param nvListeners
     * @return
     * @throws NovaException
     */
    public final static List<? extends CoreListener<?>> initListener(List<NvListener> nvListeners) throws NovaException {
        
        if (Novas.nonNullOrEmpty(nvListeners)) {
            
            List<CoreListener<?>> list = new CopyOnWriteArrayList<>();
            for (NvListener nvs : nvListeners) {
                CoreListener<?> listener;
                try {
                    listener = ObjectFactory.factory().create(ExecutionMode.get(nvs.getExecutionMode()), nvs.getValue());
                    list.add(listener);
                } catch (NovaException e) {
                    LoggerFactory.getLogger(Listeners.class).error("监听器创建异常!");
                    throw e;
                }
            }
            
            return list;
        }
        
        return null;
    }
    
    /**
     * 创建流程监听对象
     * @param nova
     * @param e
     * @return
     */
    public final static DelegateProcess createProcessDelegate(Nova nova, Exception e) {
        NvProcess process = ProcessConfigContext.getContext().getProcess();
        DelegateProcess delegate = new DelegateProcess();
        delegate.setProcessId(process.getId());
        delegate.setBizSerno(nova.getBizSerno());
        delegate.setProcessName(process.getName());
        delegate.setProcessStatus(nova.getProcessStatus());
        delegate.setException(e);
        return delegate;
    }
    
    /**
     * 创建节点监听对象
     * @param nova
     * @param e
     * @return
     */
    public final static DelegateNode createNodeDelegate(Nova nova, Exception e) {
        DelegateNode delegate = new DelegateNode();
        NvNode node = ProcessConfigContext.getContext().getCurrentNode();
        delegate.setProcessId(nova.getProcessId());
        delegate.setBizSerno(nova.getBizSerno());
        delegate.setNodeId(node.getId());
        delegate.setException(e);
        delegate.setNodeName(node.getName());
        delegate.setNodeStatus(nova.getNodeStatus());
        delegate.setNodeType(NodeType.get(node.getType()));
        delegate.setNextNodeId(nova.getNextNodeId());
        return delegate;
    }

    /**
     * 创建任务监听对象
     * @param nova
     * @param e
     * @return
     */
    public final static DelegateTask createTaskDelegate(Nova nova, Exception e) {
        NvTask task = ProcessConfigContext.getContext().getCurrentTask();
        DelegateTask delegate = new DelegateTask();
        delegate.setTaskId(nova.getNodeId());
        delegate.setBizSerno(nova.getBizSerno());
        delegate.setTaskName(task.getName());
        delegate.setException(e);
        return delegate;
    }
    
    
}
