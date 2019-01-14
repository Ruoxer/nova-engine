package com.war3.nova.core.service;

import com.war3.nova.beans.NvInsNode;

/**
 * 实例节点操作服务
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:57:19
 * @version 1.0
 */
public interface NvInsNodeService {
    
    /**
     * 获取当前代执行节点
     * @param processInstanceId
     * @return
     */
    NvInsNode getByProcessInstId(String processInstanceId);
    
    /**
     * 获取最后一个执行节点
     * @param processInstanceId
     * @return
     */
    NvInsNode getLastByProcessInstId(String processInstanceId);
    
    /**
     * 更新节点状态
     * @param nodeInstanceId
     * @param nodeStatus
     * @return
     */
    int update(NvInsNode insNode);
    
    /**
     * 数据插入
     * @param insNode
     * @return
     */
    int insert(NvInsNode insNode);
    
    /**
     * 根据节点实例号查询实例信息
     * @param instanceId
     * @return
     */
    NvInsNode getByInstanceId(String instanceId);

}
