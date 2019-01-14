package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义字段值取值类型
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午9:54:19
 * @version 1.0
 */
public enum FieldType {
    
    /**
     * 取默认值
     */
    DEFAULT, 
    
    /**
     * 从上下文中获取
     */
    CONTEXT;
    
    private final static Map<String, FieldType> types = new ConcurrentHashMap<String, FieldType>(FieldType.values().length);
    
    static {
        Arrays.stream(FieldType.values()).forEach(s -> {
            types.put(s.toString(), s);
            types.put(s.toString().toLowerCase(), s);
        });
    }
    
    public final static boolean compare(FieldType fieldType, String value) {
        return fieldType.equals(types.getOrDefault(value, null));
    }
    
    public final static FieldType get(String value) {
        return types.getOrDefault(value, null);
    }

}
