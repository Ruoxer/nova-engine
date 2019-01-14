package com.war3.nova.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.war3.nova.config.constant.FlowConstants;

/**
 * @description 流程配置相关属性，
 * @author crackLu
 * @date 2018年12月24日
 * @version 1.0
 */
@ConfigurationProperties(prefix = "flow")
public class FlowProperties {
	/**
	 * 流程发布相关参数
	 */
	private Publish publish = new Publish();

	public static class Publish {
		/**
		 * 流程发布类型
		 */
		private String type = FlowConstants.FLOW_PUBLISH_TYPE_XML;
		/**
		 * 流程发布父目录
		 */
		private String path = "file:../../flow";

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
	}

	public Publish getPublish() {
		return publish;
	}

	public void setPublish(Publish publish) {
		this.publish = publish;
	}

}
