package com.war3.nova.core.actuator.node.artificialapproval;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.beans.NvInsApproval;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.service.util.Approvals;

/**
 * 其他类型审批提交
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午3:16:58
 * @version 1.0
 */
@DefaultArtificialApprovalSubmit
public class OtherArtiAprvSubmitService implements ArtificialApprovalSubmitService {

    private final static Logger logger = LoggerFactory.getLogger(OtherArtiAprvSubmitService.class);
    
    @Override
    public ArtificialApprovalSubmitResult doSubmit(String processInstId, String nodeInstId) throws NovaException {
        List<NvInsApproval> insApprovals = Approvals.getNodeAllApproval(nodeInstId);
        long notApprovalCount = insApprovals.parallelStream().filter(s -> !Constants.COMMMON_YESNO_YES.equals(s.getSubmitted())).count();
        NvNode nvNode = ProcessConfigContext.getContext().getCurrentNode();
        if (notApprovalCount > 0) {
            logger.warn("节点实例[{}], 节点[{}]审批人尚未完全审批结束!", nodeInstId, nvNode.getId());
            return ArtificialApprovalSubmitResult.UNCOMPLETED;
        }
        
        long refuseAprvResult = insApprovals.parallelStream().filter(s -> ProcessConstants.ARRV_RESULT_REFUSE.equals(s.getAprvResult())).count();
        logger.info("节点实例[{}], 节点[{}], 为审批提交，审批结果为：{}", nodeInstId, nvNode.getId(), refuseAprvResult > 0 ? "拒绝" : "通过");
        return refuseAprvResult > 0 ? ArtificialApprovalSubmitResult.REFUSE : ArtificialApprovalSubmitResult.PASS;
    }
    
}
