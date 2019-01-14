package com.war3.nova.core.calculator.approval;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.Nova;
import com.war3.nova.core.enumeration.ApproverType;


/**
 * 
 * 任务池方式审批人员计算器
 * 
 * @author Cytus_
 * @since 2018年6月21日 下午3:09:21
 * @version 1.0
 *
 */
@NovaMapper(enumClass = ApproverType.class, enumValue = "POOL", mapperName = Constants.APPROVER_CALCULATOR)
public class PoolApproverCalculator extends AbstractApproverCalculator implements ApproverCalcuator {
    
    private static Logger logger = LoggerFactory.getLogger(PoolApproverCalculator.class);
    
    @Autowired
    ApproverPoolCalculator poolCalculator;

    @Override
    public List<Approver> calculate(Nova nova) throws NovaException {
        
        logger.info("节点实例[{}], 节点[{}]任务池计算当前处理人开始", nova.getNodeInstId(), nova.getNodeId());
        
        ApprovalProcess pool = new ApprovalProcess(nova.getProcessId(), nova.getNodeId(), nova.getBizSerno());
        List<Approver> apprvoers = poolCalculator.calculate(pool);
        
        logger.info("节点实例[{}], 节点[{}]任务池计算当前处理人结束, 当前节点配置为任务池模式!", nova.getNodeInstId(), nova.getNodeId());

        return apprvoers;
    }

}
