package com.war3.nova.core.calculator.approval.range;

import org.springframework.beans.factory.annotation.Autowired;

import com.war3.nova.Constants;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.core.enumeration.AprvRangeType;

/**
 * 岗位审批范围
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午3:25:37
 * @version 1.0
 */
@NovaMapper(enumClass = AprvRangeType.class, enumValue = "P", mapperName = Constants.APRV_RANGE_CALCULATOR)
public class PostApproverRangeCalculator extends AbstractApproverRangeCalculator {

    @Autowired
    PostRangeService postRangeService;
   
    public RangeService getRangeService() {
        return postRangeService;
    }

}
