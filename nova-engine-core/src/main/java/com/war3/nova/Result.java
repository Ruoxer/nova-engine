package com.war3.nova;

/**
 * 返回结果
 * @param <T>
 * @author Cytus_
 * @since 2018年12月27日 下午2:26:24
 * @version 1.0
 */
public interface Result<T> {
    
    public final static int SUCCESS = 0;
    
    public final static int FAILURE = -1;
    
    public final static int REFUSE = 1;
    
    public T getResult();
    
    public boolean isSuccess();
    
    public String errorCode();
    
    public String errorMessage();

}
