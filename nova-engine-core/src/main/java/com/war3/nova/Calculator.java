package com.war3.nova;

/**
 * 计算器
 * @param <I> 入参
 * @param <O> 出参
 * @author Cytus_
 * @since 2018年12月27日 下午2:23:58
 * @version 1.0
 */
public interface Calculator<I, O> {

    /**
     * 计算
     * @param arg
     * @return
     * @throws NovaException
     */
    O calculate(I arg) throws NovaException;
    
}
