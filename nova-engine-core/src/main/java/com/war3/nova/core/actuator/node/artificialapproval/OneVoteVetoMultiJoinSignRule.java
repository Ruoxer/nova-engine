package com.war3.nova.core.actuator.node.artificialapproval;

import java.util.List;

import org.springframework.stereotype.Service;

import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.beans.NvInsApproval;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.customized.BooleanResult;

/**
 * 一票否决
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午5:54:40
 * @version 1.0
 */
@Service
public class OneVoteVetoMultiJoinSignRule implements CustomizedMultiJoinSignRule {

    @Override
    public Result<?> execute(MultiJoinSignParameter parameter) throws NovaException {
        List<NvInsApproval> approvals = parameter.getApprovals();
        long refuseNum = approvals.parallelStream().filter(s -> ProcessConstants.ARRV_RESULT_REFUSE.equals(s.getAprvResult())).count();
        return BooleanResult.createSuccess(refuseNum > 0);
        
    }

}
