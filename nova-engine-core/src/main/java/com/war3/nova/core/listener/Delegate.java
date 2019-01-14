package com.war3.nova.core.listener;

import java.io.Serializable;


/**
 * 监听对象
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午1:25:08
 * @version 1.0
 */
public class Delegate implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String bizSerno;
    
    private Exception exception;
    
    public String getBizSerno() {
        return bizSerno;
    }

    public void setBizSerno(String bizSerno) {
        this.bizSerno = bizSerno;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
