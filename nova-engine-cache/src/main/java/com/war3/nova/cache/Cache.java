package com.war3.nova.cache;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @description: 流程缓存接口
 * @author: craft
 * @date: 2018/12/13
 * @version: 1.0-SNAPSHOT
 */
public interface Cache {

    /**
     * 获取最新的流程根据流程id
     *
     * @param flowId
     * @return
     */
    <T> T getLatestFlowByFlowId(String flowId);

    /**
     * 根据流程id和版本获取流程
     *
     * @param flowId
     * @param version
     * @return
     */
    <T> T getFlowByFlowIdAndVersion(String flowId, String version);

    @CachePut(cacheNames = "default", key = "#key")
    default Object add2DefaultCache(Object key, Object value) {
        return value;
    }

    /**
     * @param key
     * @return 获取自己放入的key对应的值
     */
    @Cacheable(cacheNames = "default", key = "#key")
    default Object getDefaultCacheByKey(Object key) {
        return null;
    }
}
