package com.war3.nova.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import com.war3.nova.NovaException;

/**
 * 断言工具 类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午1:58:27
 * @version 1.0
 */
public final class Asserts {

	private final static Logger logger = LoggerFactory.getLogger(Asserts.class);

	/**
	 * 对象为null抛出异常
	 * @param object
	 * @param messagePattern
	 * @param argArray
	 * @throws NovaException
	 */
    public final static void assertNull(Object object, String messagePattern, Object... argArray) throws NovaException {
        if (Objects.isNull(object)) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * 对象为null 或转换为String对象为空或null抛出异常
     * @param object
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertNullOrEmpty(Object object, String messagePattern, Object... argArray) throws NovaException {
        if (Objects.isNull(object) || Strings.isEmpty(Strings.replaceNullByObj(object))) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * 对象为空或null抛出异常
     * @param string
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertNullOrEmpty(String string, String messagePattern, Object... argArray) throws NovaException {
        if (Strings.isEmpty(string)) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * 集合对象为空或null抛出异常
     * @param collection
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertCollectionNullOrEmpty(Collection<?> collection, String messagePattern, Object... argArray) throws NovaException {
        if (Novas.isNullOrEmpty(collection)) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * Map对象为空或null抛出异常
     * @param map
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertMapNullOrEmpty(Map<?, ?> map, String messagePattern, Object... argArray) throws NovaException {
        if (Novas.isNullOrEmpty(map)) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * 小于最小值抛出异常
     * @param value
     * @param minVal
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertMinNumber(Number value, Number minVal, String messagePattern, Object... argArray) throws NovaException {
        if (minVal.doubleValue() > value.doubleValue()) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * 大于最大值抛出异常
     * @param value
     * @param maxVal
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertMaxNumber(Number value, Number maxVal, String messagePattern, Object... argArray) throws NovaException {
        if (maxVal.doubleValue() < value.doubleValue()) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * 对象相等抛出异常
     * @param obj1
     * @param obj2
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertEquals(Object obj1, Object obj2, String messagePattern, Object... argArray) throws NovaException {
        if (Objects.equals(obj1, obj2)) {
        	String message = MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
            logger.error(message);
            throw new NovaException(message);
        }
    }
    
    /**
     * 集合中存在一个相等的对象抛出异常
     * @param objs
     * @param obj2
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertEquals(Object[] objs, Object obj2, String messagePattern, Object... argArray) throws NovaException {
    	if (Novas.nonNullOrEmpty(objs)) {
	        for (Object obj : objs)
	            assertEquals(obj, obj2, messagePattern, argArray);
    	}
    }
	
    /**
     * 集合中存在一个相等的对象抛出异常
     * @param objs
     * @param obj2
     * @param messagePattern
     * @param argArray
     * @throws NovaException
     */
    public final static void assertEquals(Collection<?> objs, Object obj2, String messagePattern, Object... argArray) throws NovaException {
    	if (Novas.nonNullOrEmpty(objs)) {
	        for (Object obj : objs)
	            assertEquals(obj, obj2, messagePattern, argArray);
        }
    }
}
