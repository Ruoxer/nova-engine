package com.war3.nova.cache;


public class CacheManager {
	
	private final static Cache cache = SpringContextUtils.getBean(Cache.class);
	
    public final static <T> T getLatestProcessById(String flowId) {
    	return cache.getLatestFlowByFlowId(flowId);
    }

    /**
     * 根据流程id和版本获取流程
     *
     * @param flowId
     * @param version
     * @return
     */
    public final static <T> T getProcessByIdAndVersion(String flowId, String version) {
    	return cache.getFlowByFlowIdAndVersion(flowId, version);
    }

    public final static Object add2DefaultCache(Object key, Object value) {
        return cache.add2DefaultCache(key, value);
    }

    /**
     * @param key
     * @return 获取自己放入的key对应的值
     */
    public final static Object getDefaultCacheByKey(Object key) {
        return cache.getDefaultCacheByKey(key);
    }

}
