package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点类型
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午9:51:50
 * @version 1.0
 */
public enum NodeType {
    
    /**
     * 开始节点
     */
    START, 
    
    /**
     * 结束节点
     */
    END, 
    
    /**
     * 自动节点
     */
    AUTO, 
    
    /**
     * 人工节点
     */
    ARTI,
    
    /**
     * 子节点
     */
    SUB,
    
    /**
     * 自定义节点
     */
    CUSTOM;
    
    private final static Map<String, NodeType> types = new ConcurrentHashMap<String, NodeType>(NodeType.values().length);
    
    static {
        Arrays.stream(NodeType.values()).forEach(s -> {
            types.put(s.toString(), s);
            types.put(s.toString().toLowerCase(), s);
        });
    }
    
    public final static boolean compare(NodeType nodeType, String value) {
        return nodeType.equals(types.getOrDefault(value, null));
    }
    
    public final static NodeType get(String value) {
        return types.getOrDefault(value, null);
    }
    
}
