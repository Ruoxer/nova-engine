package com.war3.nova.core.actuator.node.artificialapproval;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.NvInsApproval;
import com.war3.nova.beans.NvMulti;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.enumeration.ApproverType;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.enumeration.MultiRuleType;
import com.war3.nova.core.factory.RunnerFactory;
import com.war3.nova.core.service.util.Approvals;
import com.war3.nova.core.util.SpringContexts;
import com.war3.nova.core.util.Strings;

/**
 * 会签审批提交
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午3:17:16
 * @version 1.0
 */
@NovaMapper(enumClass = ApproverType.class, enumValue = "MULTI", mapperName = Constants.ARTIFICIAL_APPROVAL_SUBMIT_SERVICE)
public class MultiJoinSignArtiAprvSubmitService implements ArtificialApprovalSubmitService {
    
    private final static Logger logger = LoggerFactory.getLogger(MultiJoinSignArtiAprvSubmitService.class);

    @Override
    @SuppressWarnings("unchecked")
    public ArtificialApprovalSubmitResult doSubmit(String processInstId, String nodeInstId) throws NovaException {
        
        Result<?> runnerResult ;
        NvNode nvNode = ProcessConfigContext.getContext().getCurrentNode();
        NvMulti nvMulti = nvNode.getApproval().getMulit();
        List<NvInsApproval> insApprovals = Approvals.getNodeAllApproval(nodeInstId);
        MultiJoinSignParameter parameter = new MultiJoinSignParameter(
                ProcessConfigContext.getContext().getProcess().getId(), processInstId, nvNode.getId(), nodeInstId, insApprovals);
        
        if (MultiRuleType.compare(MultiRuleType.SYSTEM, nvMulti.getRuleType())) {
            String beanName = SystemMultiRuleType.valueOf(nvMulti.getValue()).getValue();
            CustomizedMultiJoinSignRule rule = SpringContexts.getBean(beanName, CustomizedMultiJoinSignRule.class);
            logger.debug("节点实例[{}], 节点[{}]使用系统预定义会签规则执行开始", nodeInstId, nvNode.getId());
            runnerResult = rule.execute(parameter);
            
        } else {
            logger.debug("节点实例[{}], 节点[{}]自定义会签规则执行开始", nodeInstId, nvNode.getId());
            ObjectActuator<MultiJoinSignParameter, Result<?>> runner = (ObjectActuator<MultiJoinSignParameter, Result<?>>) 
                    RunnerFactory.factory().getObjectActuator(ExecutionMode.get(nvMulti.getExecutionMode()));
            runnerResult = runner.run(nvMulti.getValue(), parameter);
        }
        
        if (!runnerResult.isSuccess()) {
            throw new NovaException(runnerResult.errorCode(), runnerResult.errorMessage());
        } else {
            boolean result = Boolean.valueOf(Strings.replaceNullByObj(runnerResult.getResult()));
            logger.info("节点实例[{}], 节点[{}], 为审批提交，审批结果为：{}", nodeInstId, nvNode.getId(), result ? "拒绝" : "通过");
            return result ? ArtificialApprovalSubmitResult.PASS : ArtificialApprovalSubmitResult.REFUSE;
        }
        
        
    }

}
