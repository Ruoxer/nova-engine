package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 审批人员集合类型
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午9:44:18
 * @version 1.0
 */
public enum ApproverSetType {
    
    /**
     * 交集
     */
    I,
    
    /**
     * 并集
     */
    U,
    
    /**
     * 自定义
     */
    C;
    
    private final static Map<String, ApproverSetType> types = new ConcurrentHashMap<String, ApproverSetType>(ApproverSetType.values().length);
    
    static {
        Arrays.stream(ApproverSetType.values()).forEach(s -> {types.put(s.toString(), s);});
    }
    
    public final static boolean compare(ApproverSetType approverSetType, String value) {
        return approverSetType.equals(types.getOrDefault(value, null));
    }
    
    public final static ApproverSetType get(String value) {
        return types.getOrDefault(value, null);
    }
    
}
