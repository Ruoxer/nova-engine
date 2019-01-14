package com.war3.nova.core.customized.task;

import java.io.Serializable;
import java.util.Map;

/**
 * 任务调度参数
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午8:55:09
 * @version 1.0
 */
public class TaskParameter implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String bizSerno;
    
    private Map<String, Object> extParams;
    
    public String getBizSerno() {
        return bizSerno;
    }

    public void setBizSerno(String bizSerno) {
        this.bizSerno = bizSerno;
    }

    public Map<String, Object> getExtParams() {
        return extParams;
    }

    public void setExtParams(Map<String, Object> extParams) {
        this.extParams = extParams;
    }
    
}
