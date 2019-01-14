package com.war3.nova.core.actuator.node;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsNode;
import com.war3.nova.beans.NvNode;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.actuator.node.artificialapproval.ArtificialApprovalSubmitResult;
import com.war3.nova.core.actuator.node.artificialapproval.ArtificialApprovalSubmitService;
import com.war3.nova.core.actuator.node.artificialapproval.ArtificialApprovalSubmitServiceFactory;
import com.war3.nova.core.calculator.approval.ApproverCalcuator;
import com.war3.nova.core.enumeration.ApproverType;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.factory.CalculatorFactory;
import com.war3.nova.core.service.util.Approvals;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Strings;

/**
 * 人工节点执行器
 * 
 * @author Cytus_
 * @since 2018年12月13日 上午8:39:34
 * @version 1.0
 */
@NovaMapper(enumValue = "ARTI", enumClass = NodeType.class, mapperName = Constants.NODE_ACTUATOR)
public class ArtiNodeCoreActuator extends AbstractNodeCoreActuator {
    
    private final static Logger logger = LoggerFactory.getLogger(ArtiNodeCoreActuator.class);

    @Override
    public Nova doExecute(Nova nova) throws NovaException {
        
        nova.setNodeStatus(ProcessConstants.STATUS_RUNNING);
        NvInsNode insNode = super.initAndGetInstance(nova);
        String errorMsg = null;
        
        
        if (Strings.isEmpty(nova.getNodeInstId())) {
            nova.setNodeInstId(insNode.getInstanceId());
        }
        
        if (ProcessConstants.STATUS_END.equals(insNode.getStatus())) {
            logger.info("节点实例[{}], 节点[{}]已处理结束!", nova.getNodeInstId(), nova.getNodeId());
            return nova;
        }
        
        try {
            // 第一次进入人工处理，仅用计算审批人员信息即可
            if (ProcessConstants.STATUS_RUNNING.equals(insNode.getStatus())) {
                return this.handleApprover(nova);
            }
            
            int approverNum = Approvals.countApprover(nova.getNodeInstId());
            // 处理异常状况
            if (ProcessConstants.STATUS_EXCEPTION.equals(insNode.getStatus())) {
                // 未计算出审批人员信息
                if (approverNum <= 0) {    
                    return this.handleApprover(nova);
                } else { // 提交异常
                    insNode.setStatus(ProcessConstants.STATUS_APPROVAL);
                }
            }
            
            // 第二次进入人工处理，一般来说是审批过后提交
            if (ProcessConstants.STATUS_APPROVAL.equals(insNode.getStatus()) && Objects.nonNull(nova.getInstApproval())) {
                logger.info("节点实例[{}], 节点[{}], 进入人工审批提交处理", nova.getNodeInstId(), nova.getNodeId());
                nova.getInstApproval().setNodeInstanceId(nova.getNodeInstId());
                nova.getInstApproval().setSubmitted(Constants.COMMMON_YESNO_YES);
                Approvals.updateApproval(nova.getInstApproval());
                
                String approverType = ProcessConfigContext.getContext().getCurrentNode().getApproval().getApprovalType();
                
                ArtificialApprovalSubmitService submitService = ArtificialApprovalSubmitServiceFactory.factory().getArtificialApprovalSubmitServicer(ApproverType.get(approverType));
                ArtificialApprovalSubmitResult submitResult = submitService.doSubmit(nova.getProcessInstId(), nova.getNodeInstId());
                
                if (ArtificialApprovalSubmitResult.UNCOMPLETED.equals(submitResult)) {
                    logger.warn("节点实例[{}], 节点[{}]审批人尚未完全审批结束!", nova.getNodeInstId(), nova.getNodeId());
                    nova.setNodeStatus(ProcessConstants.STATUS_APPROVAL);
                    return nova;
                } else {
                    nova.setNodeStatus(ArtificialApprovalSubmitResult.REFUSE.equals(submitResult) ? ProcessConstants.STATUS_REFUSE : ProcessConstants.STATUS_END);
                }
                
                logger.info("节点实例[{}], 节点[{}], 人工审批提交处理结束", nova.getNodeInstId(), nova.getNodeId());
            }
        } catch (NovaException e) {
            errorMsg = Novas.formatMessage("节点实例[{}], 节点[{}]处理异常! cause by:{}", nova.getNodeInstId(), nova.getNodeId(), e.getMessage());
            logger.error(errorMsg);
            nova.setNodeStatus(ProcessConstants.STATUS_EXCEPTION);
            throw e;
        } catch (Exception e) {
            errorMsg = Novas.formatMessage("节点实例[{}], 节点[{}]处理异常! cause by:{}", nova.getNodeInstId(), nova.getNodeId(), e.getMessage());
            logger.error(errorMsg);
            nova.setNodeStatus(ProcessConstants.STATUS_EXCEPTION);
            throw new NovaException(errorMsg, e);
        } finally {
            super.updateNodeStatus(nova.getNodeInstId(), nova.getNodeStatus(), errorMsg);
        }
        return nova;
    }
    
    /**
     * 处理审批人
     * @param nova
     * @return
     * @throws NovaException
     */
    protected Nova handleApprover(Nova nova) throws NovaException {
        nova.setNodeStatus(ProcessConstants.STATUS_APPROVAL);
        NvNode node = ProcessConfigContext.getContext().getCurrentNode();
        ApproverCalcuator approverCalcuator = CalculatorFactory.factory().getApproverCalculator(ApproverType.get(node.getApproval().getApprovalType()));
        List<Approver> approvers = approverCalcuator.calculate(nova);
        Approvals.addApprovers(nova.getProcessInstId(), nova.getNodeInstId(), approvers);
        nova.setNextApprovers(approvers);
        logger.info("节点实例[{}], 节点[{}], 计算审批人员信息结束!", nova.getNodeInstId(), nova.getNodeId());
        return nova;
    }

}
