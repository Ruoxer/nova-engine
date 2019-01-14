package com.war3.nova.core.actuator.node.artificialapproval;

import java.util.List;

import org.springframework.stereotype.Service;

import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.beans.NvInsApproval;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.customized.BooleanResult;
import com.war3.nova.core.util.Numbers;

/**
 * 三分之二通过
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午6:03:35
 * @version 1.0
 */
@Service
public class Two_ThirdsPassedMultiJoinSignRule implements CustomizedMultiJoinSignRule {

    @Override
    public Result<?> execute(MultiJoinSignParameter parameter) throws NovaException {
        List<NvInsApproval> approvals = parameter.getApprovals();
        long refuseNum = approvals.parallelStream().filter(s -> ProcessConstants.ARRV_RESULT_REFUSE.equals(s.getAprvResult())).count();
        return BooleanResult.createSuccess(Numbers.divide(refuseNum, approvals.size(), 2).compareTo(Numbers.divide(1, 3, 2)) < 0);
        
    }

}
