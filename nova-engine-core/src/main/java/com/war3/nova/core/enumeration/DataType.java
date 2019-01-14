package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据类型
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午9:46:34
 * @version 1.0
 */
public enum DataType {

    /**
     * 字符串
     */
    STRING, 
    
    /**
     * 数字double类型
     */
    DOUBLE, 
    
    /**
     * 金额类型
     */
    DECIMAL, 
    
    /**
     * 数字float类型
     */
    FLOAT, 
    
    /**
     * 整型
     */
    INTEGER, 
    
    /**
     * 日期型  默认格式为yyyy-MM-dd
     */
    DATE, 
    
    /**
     * 日期时间类型  默认格式为yyyy-MM-dd HH:mm:ss
     */
    DATETIME, 
    
    /**
     * 时间类型 默认格式为HH:mm:ss
     */
    TIME, 
    
    /**
     * 布尔型
     */
    BOOLEAN;
    
    private final static Map<String, DataType> types = new ConcurrentHashMap<String, DataType>(DataType.values().length);
    
    static {
        Arrays.stream(DataType.values()).forEach(s -> {
            types.put(s.toString(), s);
            types.put(s.toString().toLowerCase(), s);
        });
    }
    
    public final static boolean compare(DataType dataType, String value) {
        return dataType.equals(types.getOrDefault(value, null));
    }
    
    public final static DataType get(String value) {
        return types.getOrDefault(value, null);
    }
    
}
