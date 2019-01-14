package com.war3.nova.core.factory;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.core.UnsupportArgumentException;
import com.war3.nova.core.calculator.approval.ApproverCalcuator;
import com.war3.nova.core.calculator.approval.ApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.ApproverSetCalculator;
import com.war3.nova.core.enumeration.ApproverSetType;
import com.war3.nova.core.enumeration.ApproverType;
import com.war3.nova.core.enumeration.AprvRangeType;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.SpringContexts;

/**
 * 
 * 计算器工厂类
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午3:24:10
 * @version 1.0
 */
public class CalculatorFactory {
    
    private final static Logger logger = LoggerFactory.getLogger(ActuatorFactory.class);
    
    private static class CalculatorFactoryHolder {
        private static final CalculatorFactory FACTORY = new CalculatorFactory();
    }
    
    private CalculatorFactory() {} 

    public final static CalculatorFactory factory() {
        return CalculatorFactoryHolder.FACTORY;
    }
    
    /**
     * 获得审批范围计算
     * @param aprvRangeType
     * @return
     * @throws NovaException
     */
    public ApproverRangeCalculator getApproverRangeCalculator(AprvRangeType aprvRangeType) throws NovaException {
        if (Objects.isNull(aprvRangeType)) {
            String errorMsg = Novas.formatMessage("Unsupported Approver Range type[{}]!", aprvRangeType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.APRV_RANGE_CALCULATOR, aprvRangeType);
        ApproverRangeCalculator calculator = SpringContexts.getBean(bean, ApproverRangeCalculator.class);
        return calculator;
    }
    
    /**
     * 获得审批人集合计算
     * @param approverSetType
     * @return
     * @throws NovaException
     */
    public ApproverSetCalculator getApproverSetCalculator(ApproverSetType approverSetType) throws NovaException {
        if (Objects.isNull(approverSetType)) {
            String errorMsg = Novas.formatMessage("Unsupported Approver set type[{}]!", approverSetType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.APRV_SET_CALCULATOR, approverSetType);
        ApproverSetCalculator calculator = SpringContexts.getBean(bean, ApproverSetCalculator.class);
        return calculator;
    }
    
    /**
     * 审批人员计算类型集合计算
     * @param approverType
     * @return
     * @throws NovaException
     */
    public ApproverCalcuator getApproverCalculator(ApproverType approverType) throws NovaException {
        if (Objects.isNull(approverType)) {
            String errorMsg = Novas.formatMessage("Unsupported Approver type[{}]!", approverType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.APPROVER_CALCULATOR, approverType);
        ApproverCalcuator calculator = SpringContexts.getBean(bean, ApproverCalcuator.class);
        return calculator;
    }

}
