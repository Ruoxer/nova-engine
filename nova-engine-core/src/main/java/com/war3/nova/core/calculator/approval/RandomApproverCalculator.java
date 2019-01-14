package com.war3.nova.core.calculator.approval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvApprover;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.enumeration.ApproverSetType;
import com.war3.nova.core.enumeration.ApproverType;
import com.war3.nova.core.factory.CalculatorFactory;
import com.war3.nova.core.util.Novas;


/**
 * 
 * 随机人员计算
 * 
 * @author Cytus_
 * @since 2018年6月26日 下午4:30:33
 * @version 1.0
 *
 */
@NovaMapper(enumClass = ApproverType.class, enumValue = "RANDOM", mapperName = Constants.APPROVER_CALCULATOR)
public class RandomApproverCalculator extends AbstractApproverCalculator implements ApproverCalcuator {
    
    private static Logger logger = LoggerFactory.getLogger(RandomApproverCalculator.class);

    @Override
    public List<Approver> calculate(Nova nova) throws NovaException {
        
        List<Approver> apprvoers = new ArrayList<Approver>();
        logger.info("节点实例[{}], 节点[{}]随机分配计算当前处理人开始", nova.getNodeInstId(), nova.getNodeId());
        
        NvNode node = ProcessConfigContext.getContext().getCurrentNode();
        
        List<NvApprover> nvApprovers = node.getApproval().getApprover();
        if (Novas.isNullOrEmpty(nvApprovers)) {
            String errorMsg = Novas.formatMessage("节点实例[{}], 节点[{}]随机分配当前配置定的审批人信息为空!", nova.getNodeInstId(), nova.getNodeId());
            logger.error(errorMsg);
            throw new NovaException(errorMsg);
        }
        
        List<List<Approver>> listApprovers = nvApprovers.parallelStream().map(s -> super.calculate(nova, s)).collect(Collectors.toList());
        ApproverSetCalculator setCalculator = CalculatorFactory.factory().getApproverSetCalculator(ApproverSetType.get(node.getApproval().getSets()));
        List<Approver> approvers = setCalculator.calculate(listApprovers);
        
        approvers = approvers.parallelStream().limit(node.getApproval().getApproverNum()).collect(Collectors.toList());
        
        if (node.getApproval().isCustomizedAprvAuth()) {
            approvers = super.executeCustomizedApproverAuthority(approvers, new ApprovalProcess(nova.getProcessId(), nova.getNodeId(), nova.getBizSerno()));
        }
        
        logger.info("节点实例[{}], 节点[{}]随机分配计算当前处理人结束, 审批人信息为:{}", nova.getNodeInstId(), nova.getNodeId());
        return apprvoers;
    }

}
