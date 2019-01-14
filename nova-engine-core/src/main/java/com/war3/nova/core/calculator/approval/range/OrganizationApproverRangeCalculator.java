package com.war3.nova.core.calculator.approval.range;

import org.springframework.beans.factory.annotation.Autowired;

import com.war3.nova.Constants;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.core.enumeration.AprvRangeType;

/**
 * 机构人员计算
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午5:33:42
 * @version 1.0
 */
@NovaMapper(enumClass = AprvRangeType.class, enumValue = "O", mapperName = Constants.APRV_RANGE_CALCULATOR)
public class OrganizationApproverRangeCalculator extends AbstractApproverRangeCalculator {

    @Autowired
    OrganizationRangeService organizationRangeService;
   
    public RangeService getRangeService() {
        return organizationRangeService;
    }

}
