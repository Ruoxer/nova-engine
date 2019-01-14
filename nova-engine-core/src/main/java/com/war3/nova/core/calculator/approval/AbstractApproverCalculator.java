package com.war3.nova.core.calculator.approval;

import java.util.List;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvApprover;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.enumeration.AprvRangeType;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.factory.CalculatorFactory;
import com.war3.nova.core.factory.ObjectFactory;
import com.war3.nova.core.util.Asserts;

/**
 * 审批人员计算器抽象类
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午3:49:45
 * @version 1.0
 */
public abstract class AbstractApproverCalculator implements ApproverCalcuator {
    
    private final static ExecutionMode[] CUSTOMIZED_APPROVER_AUTH_EXECUTION_MODES =  new ExecutionMode[] {ExecutionMode.BEAN, ExecutionMode.INVOKE};
    
    public List<Approver> calculate(Nova nova, NvApprover approver) {
        try {
            ApproverRangeCalculator calculator = CalculatorFactory.factory().getApproverRangeCalculator(AprvRangeType.get(approver.getType()));
            calculator.setCalculateApprover(approver);
            return calculate(nova);
        } catch (NovaException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    /**
     * 进行业务自定义审批人员计算扩展
     * @param approvers
     * @param approvalProcess
     * @return
     * @throws NovaException
     */
    public List<Approver> executeCustomizedApproverAuthority(List<Approver> approvers, ApprovalProcess approvalProcess) throws NovaException {
        NvNode node = ProcessConfigContext.getContext().getCurrentNode();
        ExecutionMode executionMode = ExecutionMode.get(node.getApproval().getCustomizedAprvAuthMode());
        Asserts.assertEquals(CUSTOMIZED_APPROVER_AUTH_EXECUTION_MODES, executionMode, "Unsupported Execution Mode[{}]!", executionMode);
        CustomizedApproverAuthority customizedApproverAuthority = ObjectFactory.factory().create(executionMode, node.getApproval().getCustomizedAprvAuthValue());
        return customizedApproverAuthority.doCustomizedAuthority(approvalProcess, approvers);
    }

}
