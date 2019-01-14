package com.war3.nova;

import com.war3.nova.NovaException;

/**
 * 调度器
 * @param <I> 入参
 * @param <O> 出参
 * @author Cytus_
 * @since 2018年12月27日 下午2:26:34
 * @version 1.0
 */
public interface Scheduler<I, O> {

    /**
     * 调度
     * @param inArg
     * @return
     * @throws NovaException
     */
    O dispatch(I inArg) throws NovaException;
    
}
