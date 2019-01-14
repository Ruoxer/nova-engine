package com.war3.nova.core;

/**
 * 核心监听器
 * @param <T> 监听器传入的对象泛型
 * @author Cytus_
 * @since 2018年12月27日 下午2:29:50
 * @version 1.0
 */
public interface CoreListener<T> {

    /**
     * 执行前监听
     * @param t
     */
    void beforeNotify(T t);
	
    /**
     * 执行后监听
     * @param t
     */
	void afterNotify(T t);
	
	/**
	 * 异常时监听
	 * @param t
	 */
	void exceptionNotify(T t);
	
}
