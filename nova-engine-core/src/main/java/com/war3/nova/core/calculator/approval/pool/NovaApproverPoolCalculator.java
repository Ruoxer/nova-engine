package com.war3.nova.core.calculator.approval.pool;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Approver;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.calculator.approval.ApprovalProcess;
import com.war3.nova.core.calculator.approval.ApproverPoolCalculator;

/**
 * 系统默认任务池
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午10:07:59
 * @version 1.0
 */
public class NovaApproverPoolCalculator implements ApproverPoolCalculator {

    private final static Logger logger = LoggerFactory.getLogger(NovaApproverPoolCalculator.class);
    
    @Override
    public List<Approver> calculate(ApprovalProcess pool) throws NovaException {
        
        logger.info("流程[{}], 节点[{}], 业务流水号[{}]采用系统默认审批池人员计算方式", pool.getProcessId(), pool.getNodeId(), pool.getBizSerno());
        
        List<Approver> approvers = new ArrayList<Approver>();
        
        Approver approver = new Approver();
        approver.setAprvUser(ProcessConstants.POOL_USER);
        approver.setAprvUserName(ProcessConstants.POOL_USER_NAME);
        approver.setAprvOrg(ProcessConfigContext.getContext().getProcess().getOrg());
        approver.setAprvInstOrg(ProcessConfigContext.getContext().getProcess().getInstOrg());
        
        approvers.add(approver);
        
        return approvers;
    }

}
