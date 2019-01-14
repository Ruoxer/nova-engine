package com.war3.nova.core.calculator.approval;

import java.util.List;

import com.war3.nova.beans.Approver;
import com.war3.nova.beans.NvApprover;
import com.war3.nova.core.CoreCalculator;

/**
 * 审批人员范围计算器
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午4:14:25
 * @version 1.0
 */
public interface ApproverRangeCalculator extends CoreCalculator<List<Approver>> {
    
    void setCalculateApprover(NvApprover nvApprover);
    
}
