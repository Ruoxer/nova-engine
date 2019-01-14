package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * 审批人员范围
 * @author Cytus_
 * @since 2018年12月17日 下午3:00:17
 * @version 1.0
 */
public enum AprvRangeType {
    
    /**
     * 用户
     */
    U, 
    
    /**
     * 角色
     */
    R, 
    
    /**
     * 机构
     */
    O, 
    
    /**
     * 部门
     */
    D, 
    
    /**
     * 岗位
     */
    P,
    
    /**
     * 关联关系
     */
    N,
    
    /**
     * 自定义
     */
    C;
    
    
    private final static Map<String, AprvRangeType> types = new ConcurrentHashMap<String, AprvRangeType>(AprvRangeType.values().length);
    
    static {
        Arrays.stream(AprvRangeType.values()).forEach(s -> {types.put(s.toString(), s);});
    }
    
    public final static boolean compare(AprvRangeType aprvRangeType, String value) {
        return aprvRangeType.equals(types.getOrDefault(value, null));
    }
    
    public final static AprvRangeType get(String value) {
        return types.getOrDefault(value, null);
    }

}
