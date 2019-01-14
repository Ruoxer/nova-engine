package com.war3.nova.core.customized.actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.core.customized.CustomizedActuator;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.factory.ObjectFactory;

/**
 * 
 * 自定义class运行
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午9:44:18
 * @version 1.0
 */
@NovaMapper(enumClass = ExecutionMode.class, enumValue = "INVOKE", mapperName = Constants.EXECUTION_RUNNER)
public class InvokeClassObjectActuator implements ObjectActuator<Object, Result<?>>{

    private final static Logger logger = LoggerFactory.getLogger(InvokeClassObjectActuator.class);
    
    @Override
    public Result<?> run(String value, Object parameter) throws NovaException {
        CustomizedActuator<Object> customizedActuator = ObjectFactory.factory().create(ExecutionMode.INVOKE, value);
        logger.info("当前为自定义Class模式执行!");
        return customizedActuator.execute(parameter);
        
    }

}