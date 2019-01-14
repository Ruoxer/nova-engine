package com.war3.nova.core.service.util;

import com.war3.nova.beans.NvInsNode;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.service.NvInsNodeService;
import com.war3.nova.core.util.Dates;
import com.war3.nova.core.util.SpringContexts;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月14日 上午10:28:47
 * @version 1.0
 */
public final class Nodes {
	
	private final static NvInsNodeService insNodeService = SpringContexts.getBean(NvInsNodeService.class);
	
	/**
	 * 获取当前代执行节点
	 * @param processInstanceId
	 * @return
	 */
	public final static NvInsNode getCurrentNode(String processInstanceId) {
		return insNodeService.getByProcessInstId(processInstanceId);
	}
	
	/**
	 * 获取最后一个执行节点
	 * @param processInstanceId
	 * @return
	 */
	public final static NvInsNode getLastExecuteNode(String processInstanceId) {
		return insNodeService.getLastByProcessInstId(processInstanceId);
	}
	
	/**
	 * 更新节点状态
	 * @param nodeInstanceId
	 * @param nodeStatus
	 */
	public final static void updateNodeStatus(String nodeInstanceId, String nodeStatus, String errorMsg) {
	    NvInsNode insNode = new NvInsNode();
	    insNode.setInstanceId(nodeInstanceId);
	    insNode.setStatus(nodeStatus);
	    insNode.setErrorMsg(errorMsg);
	    if (ProcessConstants.STATUS_END.equals(nodeStatus)) {
	        insNode.setEndTime(Dates.formatDateTimeByDef());
	    }
		insNodeService.update(insNode);
	}
	
	/**
	 * 数据插入
	 * @param insNode
	 */
	public final static void initNode(NvInsNode insNode) {
		if (ProcessConstants.STATUS_END.equals(insNode.getStatus())) {
			insNode.setEndTime(Dates.formatDateTimeByDef());
		}
		insNodeService.insert(insNode);
	}
	
	public final static NvInsNode getByInstaceId(String nodeInstanceId) {
	    return insNodeService.getByInstanceId(nodeInstanceId);
	}

}
