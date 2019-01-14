package com.war3.nova.core.service.util;

import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsProcess;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.service.NvInsProcessService;
import com.war3.nova.core.util.Dates;
import com.war3.nova.core.util.SpringContexts;
import com.war3.nova.core.util.Strings;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月13日 上午10:34:30
 * @version 1.0
 */
public final class Processes {
	
	private final static NvInsProcessService insProcessService = SpringContexts.getBean(NvInsProcessService.class);
	
	/**
	 * 获得流程实例信息
	 * @param processInstanceId
	 * @return
	 */
	public final static NvInsProcess getInitProcessInfo(String processInstanceId) {
		return insProcessService.getByProcessInstId(processInstanceId);
	}
	
	/**
	 * 初始化流程信息
	 * @param process
	 */
	public final static void initProcessInfo(NvInsProcess process) {
		insProcessService.insert(process);
	}
	
	/**
	 * 更新当前执行节点实例编号
	 * @param processInstId
	 * @param nodeInstId
	 */
	public final static void updateCurrentNodeInstId(String processInstId, String nodeInstId) {
	    NvInsProcess process = new NvInsProcess();
	    process.setInstanceId(processInstId);
	    process.setNodeInstanceId(nodeInstId);
	    insProcessService.update(process);
	}
	
	/**
	 * 更新状态
	 * @param processInstId
	 * @param processStatus
	 */
	public final static void updateStatus(String processInstId, String processStatus) {
	    NvInsProcess process = new NvInsProcess();
        process.setInstanceId(processInstId);
        process.setStatus(processStatus);
        if (ProcessConstants.STATUS_END.equals(processStatus)) {
            process.setEndTime(Dates.formatDateTimeByDef());
        }
        insProcessService.update(process);
	}
	
	/**
	 * 流程实例信息转nova对象
	 * @param nova
	 * @param insProcess
	 */
	public final static void toNova(Nova nova, NvInsProcess insProcess) {
		nova.setProcessId(insProcess.getProcessId());
		nova.setBizSerno(insProcess.getBizSerno());
		nova.setInstOrgId(insProcess.getInstOrgId());
		nova.setOrgId(insProcess.getOrgId());
		nova.setMainProcInstId(insProcess.getMainInstanceId());
		nova.setProcessStatus(insProcess.getStatus());
		nova.setVersion(insProcess.getVersion());
		if (Strings.isNotEmpty(insProcess.getNodeInstanceId()) && Strings.isEmpty(nova.getNodeInstId())) {
			nova.setNodeInstId(insProcess.getNodeInstanceId());
		}
	}
	

}
