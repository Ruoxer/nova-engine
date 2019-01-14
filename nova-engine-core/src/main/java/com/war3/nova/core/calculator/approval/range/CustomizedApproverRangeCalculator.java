package com.war3.nova.core.calculator.approval.range;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.NvApprover;
import com.war3.nova.core.enumeration.AprvRangeType;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.util.Novas;

/**
 * 自定义的审批人员计算
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午4:15:52
 * @version 1.0
 */
@NovaMapper(enumClass = AprvRangeType.class, enumValue = "C", mapperName = Constants.APRV_RANGE_CALCULATOR)
public class CustomizedApproverRangeCalculator extends AbstractApproverRangeCalculator {

    private final static Logger logger = LoggerFactory.getLogger(CustomizedApproverRangeCalculator.class);
    
    @Override
    public List<Approver> doCalculate(NvApprover approver, String processId, String nodeId, ProcessStartUser startUser) {
        logger.debug("流程[{}], 节点[{}]自定义审批人员计算调用开始", processId, nodeId);
        ApprovalRangeParameter parameter = new ApprovalRangeParameter(processId, nodeId, startUser);
        List<User> users = ApprovalRangeRunner.execute(ExecutionMode.get(approver.getRange()), approver.getValue(), parameter);
        List<Approver> approvers = null;
        if (Novas.nonNullOrEmpty(users)) {
            approvers = users.stream().map(s -> super.userToApprover(s)).collect(Collectors.toList());
        }
        logger.debug("流程[{}], 节点[{}]自定义审批人员计算调用结束", processId, nodeId);
        return approvers;
    }

    @Override
    public RangeService getRangeService() {
        return null;
    }
    
}
