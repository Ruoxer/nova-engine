package com.war3.nova.core.factory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.SpringContexts;
import com.war3.nova.core.util.Strings;

/**
 * 映射工厂类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:49:25
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class MapperFactory {
    
    private static Map<String, Map<Enum, String>> mapper = new ConcurrentHashMap<String, Map<Enum, String>>(); 
    
    private static MapperFactory factory;
    
    public final static MapperFactory factory() {
        if (Objects.isNull(factory)) {
            synchronized (MapperFactory.class) {
                if (Objects.isNull(factory)) {
                    factory = SpringContexts.getBean(MapperFactory.class);
                }
            }
        }
        return factory;
    }
    
    
    public String getMapper(String mapperId, Enum enums) {
        Map<? extends Enum, String> map = mapper.get(mapperId);
        return map.get(enums);
    }
    
    public Map<? extends Enum, String> getMapper(String mapperId) {
        return mapper.get(mapperId);
    }
    
    public void initMapper() {
        
        String[] beanNames = SpringContexts.getBeanNamesForAnnotation(NovaMapper.class);
        if (Novas.nonNullOrEmpty(beanNames)) {
            Arrays.stream(beanNames).forEach(MapperFactory::translateMapper);
        }
        
    }
    
    @SuppressWarnings({ "unchecked"})
    private static void translateMapper(String beanName) {
        
        NovaMapper novaMapper = SpringContexts.getBean(beanName).getClass().getAnnotation(NovaMapper.class);
        Map<Enum, String> mapperMap = null;
        if (mapper.containsKey(novaMapper.mapperName())) {
            mapperMap = mapper.get(novaMapper.mapperName());
        } else {
            mapperMap = new ConcurrentHashMap<>();
        }
        
        String[] enumValues = novaMapper.enumValue();
        for (String value : enumValues) {
            mapperMap.put(Enum.valueOf(novaMapper.enumClass(), Strings.trim(value)), beanName);
        }
        
        mapper.put(novaMapper.mapperName(), mapperMap);
    }
    
}
