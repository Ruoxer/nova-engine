package com.war3.nova.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 核心注解映射类，配置各种环节的执行器，选择器使用，根据配置的枚举类型进行SpringBean选择
 * 
 * @author Cytus_
 * @since 2018年12月13日 下午6:45:16
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NovaMapper {
    
    @SuppressWarnings("rawtypes")
    public Class<? extends Enum> enumClass();
    
    public String[] enumValue() default {};
    
    public String mapperName();
    
}
