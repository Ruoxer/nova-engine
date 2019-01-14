package com.war3.nova.core.service;

import com.war3.nova.beans.NvInsProcess;

/**
 * 实例信息服务
 * 
 * @author Cytus_
 * @since 2018年12月14日 上午10:30:29
 * @version 1.0
 */
public interface NvInsProcessService {
    
    /**
     * 根据流程实例编号获取示例信息
     * @param processInstanceId
     * @return
     */
    NvInsProcess getByProcessInstId(String processInstanceId);
    
    /**
     * 插入流程示例信息
     * @param process
     * @return
     */
    int insert(NvInsProcess process);
    
    /**
     * 更新
     * @param process
     * @return
     */
    int update(NvInsProcess process);

}
