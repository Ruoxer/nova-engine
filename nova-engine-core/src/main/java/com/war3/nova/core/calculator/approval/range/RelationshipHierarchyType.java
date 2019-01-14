package com.war3.nova.core.calculator.approval.range;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 范围层级关系
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午4:52:21
 * @version 1.0
 */
enum RelationshipHierarchyType {
    
    /**
     * 当前机构办理者
     */
    _01,
    
    /**
     * 上级机构办理者
     */
    _02,
    
    /**
     * 上级机构的下级机构办理者
     */
    _03,
    
    /**
     * 本级机构及上级机构办理者
     */
    _04,
    
    /**
     * 上上级机构办理者
     */
    _05,
    
    /**
     * 上级及上上级机构办理者
     */
    _06,
    
    /**
     * 上上级的下级机构办理者
     */
    _07,
    
    /**
     * 下级机构办理人
     */
    _08,
    
    /**
     * 流程发起者
     */
    _09,
    
    /**
     * 已办理人员
     */
    _10,
    
    /**
     * 机构领导
     */
    _11,
    
    /**
     * 同一部门
     */
    _12;
    
    
    private final static Map<String, RelationshipHierarchyType> types = new ConcurrentHashMap<String, RelationshipHierarchyType>(RelationshipHierarchyType.values().length);
    
    static {
        Arrays.stream(RelationshipHierarchyType.values()).forEach(s -> {types.put(s.toString().substring(1), s);});
    }
    
    public final static boolean compare(RelationshipHierarchyType relationshipHierarchyType, String value) {
        return relationshipHierarchyType.equals(types.getOrDefault(value, null));
    }
    
    public final static RelationshipHierarchyType get(String value) {
        return types.getOrDefault(value, null);
    }

}
