package com.war3.nova.core.calculator.approval.range;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.NVRuntimeException;
import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.factory.RunnerFactory;

/**
 * 自定义审批人信息调用
 * 
 * @author Cytus_
 * @since 2018年12月28日 上午10:51:42
 * @version 1.0
 */
public class ApprovalRangeRunner {
    
    private final static Logger logger = LoggerFactory.getLogger(ApprovalRangeRunner.class);

    @SuppressWarnings("unchecked")
    public final static List<User> execute(ExecutionMode executionMode, String value, ApprovalRangeParameter parameter) {
        try {
            ObjectActuator<ApprovalRangeParameter, Result<?>> runner = (ObjectActuator<ApprovalRangeParameter, Result<?>>) RunnerFactory.factory().getObjectActuator(executionMode);
            Result<?> result = runner.run(value, parameter);
            if (result.isSuccess()) {
                Object object = result.getResult();
                if (Objects.nonNull(object) && object instanceof List) {
                    return (List<User>) object;
                }
            }
        } catch (NovaException e) {
            logger.error("当前路由条件执行异常!", e);
            throw new NVRuntimeException(e.getErrorCode(), e.getErrorMessage(), e);
        }
        return null;
    }
    
}
