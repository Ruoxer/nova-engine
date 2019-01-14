package com.war3.nova.core.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务调度类型
 * 
 * @author Cytus_
 * @since 2018年12月27日 上午9:50:32
 * @version 1.0
 */
public enum TaskScheduleType {

    /**
     * 同步调度
     */
    SYNC, 
    
    /**
     * 异步调度
     */
    ASYNC;
    
    private final static Map<String, TaskScheduleType> types = new ConcurrentHashMap<String, TaskScheduleType>(TaskScheduleType.values().length);
    
    static {
        Arrays.stream(TaskScheduleType.values()).forEach(s -> {
            types.put(s.toString(), s);
            types.put(s.toString().toLowerCase(), s);
        });
    }
    
    public final static boolean compare(TaskScheduleType taskScheduleType, String value) {
        return taskScheduleType.equals(types.getOrDefault(value, null));
    }
    
    public final static TaskScheduleType get(String value) {
        return types.getOrDefault(value, null);
    }
    
}
