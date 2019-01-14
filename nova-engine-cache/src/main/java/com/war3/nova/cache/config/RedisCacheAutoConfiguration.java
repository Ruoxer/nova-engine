package com.war3.nova.cache.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.war3.nova.cache.Cache;
import com.war3.nova.cache.DefaultProcessCache;
import com.war3.nova.config.FlowProperties;

/**
 * @description 基于redis配置的缓存自动配置
 * @author crackLu
 * @date 2018年12月28日
 * @version 0.0.1.SNAPSHOT
 */
@Configuration
@EnableConfigurationProperties({ CacheNamesProperties.class })
@EnableCaching
public class RedisCacheAutoConfiguration {
	private CacheNamesProperties cacheNamesProperties;

	public RedisCacheAutoConfiguration(CacheNamesProperties cacheNamesProperties) {
		super();
		this.cacheNamesProperties = cacheNamesProperties;
	}

	public static final String DEFAULT = "default";
	public static final String FLOW = "flow";
	private final RedisCacheConfiguration defaultConfiguration = RedisCacheConfiguration.defaultCacheConfig()
			.disableCachingNullValues().entryTtl(Duration.ofHours(6));
	private final RedisCacheConfiguration flowConfig = RedisCacheConfiguration.defaultCacheConfig()
			.disableCachingNullValues().entryTtl(Duration.ofDays(3));

	/**
	 * @description 当存在redis的时候使用redismanager
	 * @param connectionFactory
	 * @return
	 */
	@ConditionalOnBean({ RedisConnectionFactory.class })
	@Bean
	public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		Map<String, RedisCacheConfiguration> configMap = null;
		if (cacheNamesProperties != null && cacheNamesProperties.getCacheNames() != null) {
			configMap = cacheNamesProperties.getCacheNames();
		}
		if (configMap == null) {
			configMap = new HashMap<>();
		}
		if (configMap.get(DEFAULT) == null) {
			configMap.put(DEFAULT, defaultConfiguration);
		}

		if (configMap.get(FLOW) == null) {
			configMap.put(FLOW, flowConfig);
		}

		return RedisCacheManager.builder(connectionFactory).withInitialCacheConfigurations(configMap).build();
	}

	/**
	 * @description 当不存在其他Cache实现的时候使用默认
	 * @param flowProperties
	 * @return
	 */
	@ConditionalOnMissingBean(Cache.class)
	@Bean
	public Cache defaultCache(FlowProperties flowProperties) {
		return new DefaultProcessCache(flowProperties);
	}
	
	@Bean
	@ConditionalOnBean(Cache.class)
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
	    RedisTemplate redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(redisConnectionFactory);
	    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
	    redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
	    redisTemplate.afterPropertiesSet();
	    return redisTemplate;
	}
}
