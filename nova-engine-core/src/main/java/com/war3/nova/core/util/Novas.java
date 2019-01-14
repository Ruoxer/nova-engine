package com.war3.nova.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.helpers.MessageFormatter;

/**
 * 工具类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午3:03:57
 * @version 1.0
 */
public final class Novas {
    
    /**
     * 判断集合是否为null或空
     * @param collection
     * @return
     */
    public final static boolean isNullOrEmpty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }
    
    /**
     * 判断集合不为null和空
     * @param collection
     * @return
     */
    public final static boolean nonNullOrEmpty(Collection<?> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }
    
    /**
     * 判断MAP是否为null或空
     * @param map
     * @return
     */
    public final static boolean isNullOrEmpty(Map<?, ?> map) {
        return Objects.isNull(map) || map.isEmpty();
    }
    
    /**
     * 判断Map不为null和空
     * @param map
     * @return
     */
    public final static boolean nonNullOrEmpty(Map<?, ?> map) {
        return Objects.nonNull(map) && !map.isEmpty();
    }
    
    /**
     * 判断对象不为空且转化为string后不为空字符串
     * @param object
     * @return
     */
    public final static boolean nonNull(Object object) {
        return Objects.nonNull(object) && Strings.isNotEmpty(Strings.replaceNullByObj(object));
    }
    
    /**
     * 判断数组不为null和空
     * @param objects
     * @return
     */
    public final static boolean nonNullOrEmpty(Object[] objects) {
        return Objects.nonNull(objects) && objects.length > 0;
    }
    
    /**
     * 判断数组为null或空
     * @param objects
     * @return
     */
    public final static boolean isNullOrEmpty(Object[] objects) {
        return Objects.isNull(objects) || objects.length < 1;
    }
    
    /**
     * 为空获取另一个对象
     * @param srcObject
     * @param elseObject
     * @return
     */
    public final static <T> T ofNullableElse(T srcObject, T elseObject) {
        return Optional.ofNullable(srcObject).orElse(elseObject);
    }
    
    /**
     * 出去map中值为空的key
     * @param paramObject
     * @return 线程安全的ConcurrentHashMap
     */
    public final static Map<String, Object> removeNullValue(Map<String, Object> paramObject) {
        Map<String, Object> dataMap = new ConcurrentHashMap<String, Object>();
        if (Objects.nonNull(paramObject) && !paramObject.isEmpty()) {
           paramObject.entrySet().parallelStream().filter(s -> nonNull(s.getValue())).forEach(s -> dataMap.put(s.getKey(), s.getValue()));
        }
        return dataMap;
    }
    
    /**
     * 去除某些key
     * @param paramMap
     * @param keys
     * @return 线程安全的ConcurrentHashMap
     */
    public final static Map<String, Object> removeKeys(Map<String, Object> paramMap, String... keys) {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();
        if (nonNullOrEmpty(paramMap) && nonNullOrEmpty(keys)) {
            List<String> listKey = Arrays.asList(keys); 
            paramMap.entrySet().parallelStream().filter(s -> !listKey.contains(s.getKey())).forEach(s -> dataMap.put(s.getKey(), s.getValue()));
        }
        return dataMap;
    }
    
    /**
     * 判断对象是否存在
     * @param object
     * @param objects
     * @return
     */
    public final static boolean exists(Object object, Object... objects) {
        if (objects == null && object == null) return true;
        return nonNullOrEmpty(objects) ? Arrays.stream(objects).filter(s -> s.equals(object)).count() > 0 : false;
    }
    
    /**
     * 格式化字符串，采用slf4j的方式
     * @param messagePattern
     * @param objects
     * @return
     */
    public final static String formatMessage(String messagePattern, Object... objects) {
        return MessageFormatter.arrayFormat(messagePattern, objects).getMessage();
    }
    
}
