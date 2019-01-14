package com.war3.nova.core.calculator.approval;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.enumeration.ApproverType;
import com.war3.nova.core.util.Novas;


/**
 * 
 * 指定的审批人信息
 * 
 * @author Cytus_
 * @since 2018年7月4日 上午8:45:16
 * @version 1.0
 *
 */
@NovaMapper(enumClass = ApproverType.class, enumValue = "POINT", mapperName = Constants.APPROVER_CALCULATOR)
public class AppointApproverCalculator extends AbstractApproverCalculator implements ApproverCalcuator {
    
    private static Logger logger = LoggerFactory.getLogger(AppointApproverCalculator.class);

    @Override
    public List<Approver> calculate(Nova nova) throws NovaException {
        
        logger.info("节点实例[{}], 节点[{}]指定审批人计算当前处理人开始", nova.getNodeInstId(), nova.getNodeId());
        
        List<Approver> approvers = nova.getNextApprovers();
        if (Novas.isNullOrEmpty(approvers)) {
            String errorMsg = Novas.formatMessage("节点实例[{}], 节点[{}]当前指定的审批人信息为空!", nova.getNodeInstId(), nova.getNodeId());
            logger.error(errorMsg);
            throw new NovaException(errorMsg);
        }
        
        NvNode node = ProcessConfigContext.getContext().getCurrentNode();
        int approverNum = node.getApproval().getApproverNum();
        List<Approver> aprvs = approvers.stream().limit(approverNum).collect(Collectors.toList());
        logger.info("节点实例[{}], 节点[{}]指定审批人计算当前处理人结束, 处理人信息为:{}", nova.getNodeInstId(), nova.getNodeId(), aprvs);
        return aprvs;
    }
    
}
