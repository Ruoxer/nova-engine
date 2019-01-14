package com.war3.nova.core.calculator.approval.range;

import org.springframework.beans.factory.annotation.Autowired;

import com.war3.nova.Constants;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.core.enumeration.AprvRangeType;

/**
 * 部门
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午4:45:45
 * @version 1.0
 */
@NovaMapper(enumClass = AprvRangeType.class, enumValue = "D", mapperName = Constants.APRV_RANGE_CALCULATOR)
public class DepartmentApproverRangeCalculator extends AbstractApproverRangeCalculator {

    @Autowired
    DepartmentRangeService departmentRangeService;
   
    public RangeService getRangeService() {
        return departmentRangeService;
    }

}
