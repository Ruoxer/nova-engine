package com.war3.nova.core.calculator.approval.range;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvApprover;
import com.war3.nova.core.enumeration.AprvRangeType;
import com.war3.nova.core.util.Asserts;

/**
 * 关联关系审批人配置
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午5:36:26
 * @version 1.0                                                                                                 
 */
@NovaMapper(enumClass = AprvRangeType.class, enumValue = "N", mapperName = Constants.APRV_RANGE_CALCULATOR)
public class RelationshipApproverRangeCalculator extends AbstractApproverRangeCalculator {

    private final static Logger logger = LoggerFactory.getLogger(RelationshipApproverRangeCalculator.class);
    
    @Autowired
    RelationshipHierarchyService relationshipHierarchyService;
    
    public List<Approver> calculate(Nova nova) throws NovaException {
        ProcessStartUser startUser = new ProcessStartUser(nova.getStartUser(), nova.getOrgId(), nova.getInstOrgId());
        NvApprover nvApprover = approver.get();
        
        RelationshipHierarchyType type = RelationshipHierarchyType.get(nvApprover.getRange());
        Asserts.assertNull(type, "流程[{}]当前配置的关联关系审批人信息未配置范围", nova.getProcessId());
        logger.info("流程实例[{}], 节点[{}], 当前配置的关联关系类型为:", nova.getProcessInstId(), nova.getNodeId(), type);
        
        List<User> approvers = null;
        switch (type) {
            case _01 : approvers = relationshipHierarchyService.getCurrentOrg(startUser); break;
            case _02 : approvers = relationshipHierarchyService.getSuperOrg(startUser); break;
            case _03 : approvers = relationshipHierarchyService.getSuperSubOrg(startUser); break;
            case _04 : approvers = relationshipHierarchyService.getCurrAndSuperOrg(startUser); break;
            case _05 : approvers = relationshipHierarchyService.getDoubleSuperOrg(startUser); break;
            case _06 : approvers = relationshipHierarchyService.getSuperAndDoubleSuperOrg(startUser); break;
            case _07 : approvers = relationshipHierarchyService.getDoubleSuperSubOrg(startUser); break;
            case _08 : approvers = relationshipHierarchyService.getSubOrg(startUser); break;
            case _09 : approvers = relationshipHierarchyService.getProcessStartUser(startUser); break;
            case _10 : approvers = relationshipHierarchyService.getApprovedUser(nova.getProcessInstId()); break;
            case _11 : approvers = relationshipHierarchyService.getOrgLeader(startUser); break;
            case _12 : approvers = relationshipHierarchyService.getCurrDept(startUser); break;
        }
        
        return approvers.parallelStream().map(s -> userToApprover(s)).collect(Collectors.toList());
        
    }

    @Override
    public RangeService getRangeService() {
        return null;
    }
    
    
}
