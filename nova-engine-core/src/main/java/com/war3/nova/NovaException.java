package com.war3.nova;

/**
 * 异常
 * 
 * @author Cytus_
 * @since 2018年12月13日 下午7:26:44
 * @version 1.0
 */
public class NovaException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String errorCode = "999999";
    
    private String errorMessage = "System Exception!";
    
    public NovaException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public NovaException(String errorMessage, Throwable throwable) {
        super(throwable);
        this.errorMessage = errorMessage;
    }
    
    public NovaException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    
    
    public NovaException(Throwable throwable) {
        super(throwable);
        this.errorMessage = throwable.getMessage();
    }
    
    public NovaException(String errorCode, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public NovaException() {
        super();
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }
    
    
}
