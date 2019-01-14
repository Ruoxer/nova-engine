package com.war3.nova.core.actuator.node.artificialapproval;


import com.war3.nova.NovaException;

/**
 * 人工审批提交处理服务接口
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午3:08:15
 * @version 1.0
 */
public interface ArtificialApprovalSubmitService {
    
    ArtificialApprovalSubmitResult doSubmit(String processInstId, String nodeInstId) throws NovaException;

}
