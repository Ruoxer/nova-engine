package com.war3.nova.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.cache.config.RedisCacheAutoConfiguration;
import com.war3.nova.config.FlowProperties;
import com.war3.nova.config.exception.NovaConfigException;
import com.war3.nova.config.util.ObjectMapperFactory;

/**
 * @description 默认缓存的实现
 * @author crackLu
 * @date 2018年12月28日
 * @version 0.0.1.SNAPSHOT
 */
public class DefaultProcessCache implements Cache {

	private FlowProperties flowProperties;

	public DefaultProcessCache(com.war3.nova.config.FlowProperties flowProperties) {
		super();
		this.flowProperties = flowProperties;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NvProcess getLatestFlowByFlowId(String flowId) {
		return getFlowByFlowIdAndVersion(flowId, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = RedisCacheAutoConfiguration.FLOW, key = "#flowId+-#version")
	public NvProcess getFlowByFlowIdAndVersion(String flowId, String version) {
		if (flowProperties == null || flowProperties.getPublish() == null) {
			throw new NovaConfigException("请配置必须参数!");
		}
		String parentPath = flowProperties.getPublish().getPath();
		String type = flowProperties.getPublish().getType();
		try {
			File latestFile = null;
			if (StringUtils.isEmpty(version)) {
				File parentFile = ResourceUtils.getFile(parentPath);
				File flowFile = new File(parentFile + File.separator + flowId);
				if (flowFile != null && flowFile.isDirectory()) {
					latestFile = Arrays.asList(flowFile.listFiles()).stream()
							.filter(file -> file.getName().endsWith("." + type))
							.sorted(Comparator.comparing(File::getName).reversed()).findFirst().get();
				}
			} else {
				latestFile = ResourceUtils
						.getFile(parentPath + File.separator + flowId + File.separator + version + "." + type);
			}

			ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper(type);
			return objectMapper.readValue(latestFile, NvProcess.class);
		} catch (FileNotFoundException e) {
			throw new NovaConfigException("未找到发布流程文件!");
		} catch (JsonParseException e) {
			throw new NovaConfigException("流程文件格式不正确!");
		} catch (JsonMappingException e) {
			throw new NovaConfigException("流程文件格式不正确!");
		} catch (IOException e) {
			throw new NovaConfigException("解析流程文件出错!");
		}
	}

}
