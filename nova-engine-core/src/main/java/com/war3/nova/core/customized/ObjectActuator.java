package com.war3.nova.core.customized;

import com.war3.nova.NovaException;

/**
 * 自定义运行者
 * @param <P>
 * @param <O>
 * @author Cytus_
 * @since 2018年12月27日 下午2:43:21
 * @version 1.0
 */
public interface ObjectActuator<P, O> {

    public O run(String value, P parameter) throws NovaException;
    
}
