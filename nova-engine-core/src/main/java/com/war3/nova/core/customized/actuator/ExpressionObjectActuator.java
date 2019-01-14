package com.war3.nova.core.customized.actuator;

import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.customized.ObjectResult;
import com.war3.nova.core.enumeration.ExecutionMode;

/**
 * 表达式执行
 * @author Cytus_
 *
 */
@NovaMapper(enumClass = ExecutionMode.class, enumValue = "EXPRESSION", mapperName = Constants.EXECUTION_RUNNER)
public class ExpressionObjectActuator implements ObjectActuator<Object, Result<?>> {

    private final static Logger logger = LoggerFactory.getLogger(ExpressionObjectActuator.class);
    
    @Override
    public Result<?> run(String value, Object parameter) throws NovaException {
        logger.info("当前为自定义Expression模式执行!");
        Object object = MVEL.eval(value, parameter);
        return ObjectResult.createSuccess(object);
    }
    
}
