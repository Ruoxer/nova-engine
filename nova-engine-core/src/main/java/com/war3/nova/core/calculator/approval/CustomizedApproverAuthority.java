package com.war3.nova.core.calculator.approval;

import java.util.List;

import com.war3.nova.beans.Approver;

/**
 * 自定义的审批流程信息
 * 
 * @author Cytus_
 * @since 2018年12月20日 下午4:53:15
 * @version 1.0
 */
public interface CustomizedApproverAuthority {
    
    List<Approver> doCustomizedAuthority(ApprovalProcess approvalProcess, List<Approver> approvers);

}
