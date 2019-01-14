package com.war3.nova.support.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.war3.nova.core.factory.MapperFactory;

@Configuration
public class NovaMapperConfiguation {

    /**
     * 设置为lazy加载
     * @return
     */
    @Bean(initMethod="initMapper")
    @Lazy
    public MapperFactory mapperFactory() {
        return new MapperFactory();
    }
    
}
