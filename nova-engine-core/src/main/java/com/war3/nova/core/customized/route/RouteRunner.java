package com.war3.nova.core.customized.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.NVRuntimeException;
import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.factory.RunnerFactory;
import com.war3.nova.core.util.Strings;

/**
 * 自定义路由执行者
 * 
 * @author Cytus_
 * @since 2018年12月13日 上午8:37:42
 * @version 1.0
 */
public class RouteRunner {
    
    private final static Logger logger = LoggerFactory.getLogger(RouteRunner.class);
    
    @SuppressWarnings("unchecked")
    public static final boolean executeRoute(ExecutionMode executionMode, String value, RouteParameter routeParameter) {
        try {
            ObjectActuator<RouteParameter, Result<?>> runner = (ObjectActuator<RouteParameter, Result<?>>) RunnerFactory.factory().getObjectActuator(executionMode);
            Result<?> result = runner.run(value, routeParameter);
            if (result.isSuccess()) {
                return Boolean.valueOf(Strings.replaceNullByObj(result.getResult()));
            }
        } catch (NovaException e) {
            logger.error("当前路由条件执行异常!", e);
            throw new NVRuntimeException(e.getErrorCode(), e.getErrorMessage(), e);
        }
        return false;
    }

}
