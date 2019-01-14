package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会签规则类型
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午5:13:47
 * @version 1.0
 */
public enum MultiRuleType {

    /**
     * 系统预定义
     */
    SYSTEM, 
    
    /**
     * 自定义
     */
    CUSTOM;
    
    private final static Map<String, MultiRuleType> types = new ConcurrentHashMap<String, MultiRuleType>(MultiRuleType.values().length);
    
    static {
        Arrays.stream(MultiRuleType.values()).forEach(s -> {
            types.put(s.toString(), s);
            types.put(s.toString().toLowerCase(), s);
        });
    }
    
    public final static boolean compare(MultiRuleType multiRuleType, String value) {
        return multiRuleType.equals(types.getOrDefault(value, null));
    }
    
    public final static MultiRuleType get(String value) {
        return types.getOrDefault(value, null);
    }
}
