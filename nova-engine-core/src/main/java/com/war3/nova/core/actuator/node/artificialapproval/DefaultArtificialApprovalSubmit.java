package com.war3.nova.core.actuator.node.artificialapproval;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 默认的审批提交操作
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午3:50:37
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultArtificialApprovalSubmit {

    boolean isPrimary() default false;
    
}
