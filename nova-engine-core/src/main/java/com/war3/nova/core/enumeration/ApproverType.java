package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 审批人员计算类型
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午4:14:03
 * @version 1.0
 */
public enum ApproverType {

    /**
     * 任务池
     */
    POOL, 
    
    /**
     * 竞办
     */
    COMPETE, 
    
    /**
     * 指定人
     */
    POINT, 
    
    /**
     * 会签
     */
    MULTI, 
    
    /**
     * 随机分配
     */
    RANDOM, 
    
    /**
     * 自定义
     */
    CUSTOM;
    
    
    private final static Map<String, ApproverType> types = new ConcurrentHashMap<String, ApproverType>(ApproverType.values().length);
    
    static {
        Arrays.stream(ApproverType.values()).forEach(s -> {
            types.put(s.toString(), s);
            types.put(s.toString().toLowerCase(), s);
        });
    }
    
    public final static boolean compare(ApproverType approverType, String value) {
        return approverType.equals(types.getOrDefault(value, null));
    }
    
    public final static ApproverType get(String value) {
        return types.getOrDefault(value, null);
    }
    
}
