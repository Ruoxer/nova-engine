package com.war3.nova.cache;

import java.lang.annotation.Annotation;
import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;
    
    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        context = arg0;
    }
    
    public static Object getBean(String beanId) {
        assertContext();
        return context.getBean(beanId);
    }
    
    public static <T>  T getBean(String beanId, Class<T> clazz) {
        assertContext();
        return context.getBean(beanId, clazz);
    }
    
    public static void assertContext() {
        if (Objects.isNull(context)) {
            throw new NullPointerException();
        }
    }

    public static <T> T getBean(Class<T> type) {
        assertContext();
        return context.getBean(type);
    }
    
    public static String[] getBeanNamesForType(Class<?> clazz) {
        assertContext();
        return context.getBeanNamesForType(clazz);
    }
    
    public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> clazz) {
        assertContext();
        return context.getBeanNamesForAnnotation(clazz);
    }
    
}
