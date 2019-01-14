package com.war3.nova.core.service;

import com.war3.nova.beans.NvInsExtParams;

/**
 * 流程扩展参数服务
 * 
 * @author Cytus_
 * @since 2018年12月26日 上午10:42:08
 * @version 1.0
 */
public interface NvInsExtParamsService {
    
    /**
     * 根据流程实例号查询
     * @param processInstId
     * @return
     */
    NvInsExtParams queryByProcessInstId(String processInstId);
    
    /**
     * 插入
     * @param params
     * @return
     */
    int insert(NvInsExtParams params);

}
