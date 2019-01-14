package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由条件类型
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午9:51:22
 * @version 1.0
 */
public enum RouteConditionType {

    /**
     * 默认路由
     */
    DEFAULT, 
    
    /**
     * 条件路由
     */
    CONDITION;
    
    private final static Map<String, RouteConditionType> types = new ConcurrentHashMap<String, RouteConditionType>(RouteConditionType.values().length);
    
    static {
        Arrays.stream(RouteConditionType.values()).forEach(s -> {
            types.put(s.toString(), s);
            types.put(s.toString().toLowerCase(), s);
        });
    }
    
    public final static boolean compare(RouteConditionType routeConditionType, String value) {
        return routeConditionType.equals(types.getOrDefault(value, null));
    }
    
    public final static RouteConditionType get(String value) {
        return types.getOrDefault(value, null);
    }
    
}
