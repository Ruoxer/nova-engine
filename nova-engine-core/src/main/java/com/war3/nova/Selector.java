package com.war3.nova;

/**
 * 选择器
 * @param <I> 入参
 * @param <O> 出参
 * @author Cytus_
 * @since 2018年12月27日 下午2:27:07
 * @version 1.0
 */
public interface Selector<I, O> {

    /**
     * 选择
     * @param arg
     * @return
     * @throws NovaException
     */
    O select(I arg) throws NovaException;
    
}
