package com.war3.nova.core.factory;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.core.UnsupportArgumentException;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.SpringContexts;


/**
 * 
 * 执行器工厂
 * 
 * @author Cytus_
 * @since 2018年6月26日 下午2:14:20
 * @version 1.0
 *
 */
public class RunnerFactory {
    
    private final static Logger logger = LoggerFactory.getLogger(RunnerFactory.class);
    
    private static class RunnerFactoryHolder {
        private static final RunnerFactory FACTORY = new RunnerFactory();
    }
    
    private RunnerFactory() {} 

    public final static RunnerFactory factory() {
        return RunnerFactoryHolder.FACTORY;
    }
    
    public ObjectActuator<?, ?> getObjectActuator(ExecutionMode executionMode) throws NovaException {
        if (Objects.isNull(executionMode)) {
            String errorMsg = Novas.formatMessage("Unsupported Execution Mode[{}]!", executionMode);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.EXECUTION_RUNNER, executionMode);
        ObjectActuator<?, ?> customizedRunner = SpringContexts.getBean(bean, ObjectActuator.class);
        return customizedRunner;
    }
    
}
