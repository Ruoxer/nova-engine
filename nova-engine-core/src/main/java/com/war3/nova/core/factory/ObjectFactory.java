package com.war3.nova.core.factory;

import com.war3.nova.NovaException;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.util.Beans;
import com.war3.nova.core.util.SpringContexts;

/**
 * 对象创建工厂类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:49:39
 * @version 1.0
 */
public class ObjectFactory {
    
    
    private static class ObjectFactoryHolder {
        private static final ObjectFactory FACTORY = new ObjectFactory();
    }
    
    private ObjectFactory() {} 

    public final static ObjectFactory factory() {
        return ObjectFactoryHolder.FACTORY;
    }
    
    public <T> T create(ExecutionMode executionMode, String value) throws NovaException {
        T t = null;
        switch (executionMode) {
            case BEAN : t = getBeanTask(value); break;
            case INVOKE : t = getClassTask(value); break;
            default : break;
        }
        return t;
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T getBeanTask(String value) {
        return (T) SpringContexts.getBean(value);
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T getClassTask(String value) throws NovaException {
        
        try {
            return (T) Beans.newInstance(Class.forName(value));
        } catch (ClassNotFoundException e) {
            throw new NovaException("");
        }
        
    }
    
}
