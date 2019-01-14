package com.war3.nova.core.service;

/**
 * 迁移至历史信息服务
 * 
 * @author Cytus_
 * @since 2018年12月25日 下午2:18:48
 * @version 1.0
 */
public interface HistoryService {
    
    /**
     * 迁移
     * @param processInstId 流程实例号
     * @return
     */
    boolean toHistory(String processInstId);

}
