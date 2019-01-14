package com.war3.nova.config.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.war3.nova.config.constant.FlowConstants;
import com.war3.nova.config.exception.NovaConfigException;

/**
 * @description 根据不同配置获取文件操作Mapper
 * @author crackLu
 * @date 2018年12月28日
 * @version 0.0.1.SNAPSHOT
 */
public class ObjectMapperFactory {
	/**
	 * @description 工厂方法获取对象映射文件
	 * @param type
	 * @return
	 */
	public static ObjectMapper getObjectMapper(String type) {
		ObjectMapper objectMapper;
		switch (type) {
		case FlowConstants.FLOW_PUBLISH_TYPE_JSON:
			objectMapper = new ObjectMapper();
			break;
		case FlowConstants.FLOW_PUBLISH_TYPE_XML:
			objectMapper = new XmlMapper();
			break;
		default:
			throw new NovaConfigException("不正确的文件类型！");
		}

		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
}
