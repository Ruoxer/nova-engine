package com.war3.nova.core.calculator.approval.range;

import org.springframework.beans.factory.annotation.Autowired;

import com.war3.nova.Constants;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.core.enumeration.AprvRangeType;

/**
 * 根据角色计算
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午5:31:43
 * @version 1.0
 */
@NovaMapper(enumClass = AprvRangeType.class, enumValue = "R", mapperName = Constants.APRV_RANGE_CALCULATOR)
public class RoleApproverRangeCalculator extends AbstractApproverRangeCalculator {

    @Autowired
    RoleRangeService roleRangeService;
   
    public RangeService getRangeService() {
        return roleRangeService;
    }

}
