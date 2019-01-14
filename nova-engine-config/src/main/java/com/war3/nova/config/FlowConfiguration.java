package com.war3.nova.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description 流程自定义bean载入
 * @author crackLu
 * @date 2018年12月24日
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties({ FlowProperties.class })
public class FlowConfiguration {
	
}
