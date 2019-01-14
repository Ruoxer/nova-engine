package com.war3.nova;

/**
 * 执行器
 * @param <I> 入参
 * @param <O> 出参
 * @author Cytus_
 * @since 2018年12月27日 下午2:23:33
 * @version 1.0
 */
public interface Actuator<I, O> {

    /**
     * 执行
     * @param arg
     * @return
     * @throws NovaException
     */
    O execute(I arg) throws NovaException;
    
}
