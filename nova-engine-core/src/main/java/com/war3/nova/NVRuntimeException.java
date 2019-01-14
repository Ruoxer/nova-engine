package com.war3.nova;

/**
 * runtime exception
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午8:32:32
 * @version 1.0
 */
public class NVRuntimeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String errorCode = "999999";
    
    private String errorMessage = "System Exception!";
    
    public NVRuntimeException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public NVRuntimeException(String errorMessage, Throwable throwable) {
        super(throwable);
        this.errorMessage = errorMessage;
    }
    
    public NVRuntimeException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    
    
    public NVRuntimeException(Throwable throwable) {
        super(throwable);
        this.errorMessage = throwable.getMessage();
    }
    
    public NVRuntimeException(String errorCode, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public NVRuntimeException() {
        super();
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }

}
