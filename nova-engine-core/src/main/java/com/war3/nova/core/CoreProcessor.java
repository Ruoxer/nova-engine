package com.war3.nova.core;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;

/**
 * 核心流程处理器
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:30:41
 * @version 1.0
 */
public interface CoreProcessor {
	
    /**
     * 初始化流程
     * @param nova
     * @return
     * @throws NovaException
     */
	Nova initProcess(Nova nova) throws NovaException;
	
	/**
	 * 提交流程
	 * @param nova
	 * @return
	 * @throws NovaException
	 */
	Nova submit(Nova nova) throws NovaException;
	
}
