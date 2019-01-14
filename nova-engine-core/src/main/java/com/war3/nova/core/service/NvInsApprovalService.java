package com.war3.nova.core.service;

import java.util.List;

import com.war3.nova.beans.NvInsApproval;

/**
 * 审批人信息服务
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:53:58
 * @version 1.0
 */
public interface NvInsApprovalService {
    
    /**
     * 插入
     * @param insApproval
     * @return
     */
    int insert(NvInsApproval insApproval);
    
    /**
     * 批量插入
     * @param insApprovals
     * @return
     */
    int addApprovers(List<NvInsApproval> insApprovals);
    
    /**
     * 更新
     * @param insApproval
     * @return
     */
    int updateApproval(NvInsApproval insApproval);
    
    /**
     * 根据节点实例号查询所有信息
     * @param nodeInstanceId
     * @return
     */
    List<NvInsApproval> getAllByNodeInstId(String nodeInstanceId);
    
    /**
     * 删除节点实例下除传入的approvalids以外的审批信息
     * @param nodeInstId 节点实例
     * @param notDelApprovalId 不需要删除的approvalid
     */
    int deleteOtherAprvIdByNodeInstId(String nodeInstId, List<String> notDelApprovalId);

}
