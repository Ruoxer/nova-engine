package com.war3.nova.beans;

import java.io.Serializable;

/**
 * 流程定义的扩展参数
 * 
 * @author Cytus_
 * @since 2018年12月26日 上午10:40:36
 * @version 1.0
 */
public class NvInsExtParams implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String processInstId;
    
    private String extParams;

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getExtParams() {
        return extParams;
    }

    public void setExtParams(String extParams) {
        this.extParams = extParams;
    }
    
}
