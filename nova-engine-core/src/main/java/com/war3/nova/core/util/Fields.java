package com.war3.nova.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.war3.nova.beans.NvField;
import com.war3.nova.core.enumeration.DataType;
import com.war3.nova.core.enumeration.FieldType;

/**
 * 值配置处理工具类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午3:10:42
 * @version 1.0
 */
public class Fields {

    /**
     * 通过自定义配置转化成需要的配置值
     * @param fields
     * @param extParams
     * @return
     */
    public final static Map<String, Object> toCustomizedParameter(List<NvField> fields, Map<String, Object> extParams) {
        Map<String, Object> dataMap = new HashMap<>();
        if (Novas.nonNullOrEmpty(fields)) {
            fields.stream().forEach(s -> dataMap.put(s.getName(), getValue4ExtParams(s, extParams)));
        }
        return dataMap;
    }
    
    
    /**
     * 单个配置转化
     * @param field
     * @param extParams
     * @return
     */
    public final static Object getValue4ExtParams(NvField field, Map<String, Object> extParams) {
        Object value = null;
        if (FieldType.compare(FieldType.DEFAULT, field.getValueType())) {
            value = field.getValue();
        } else {
            value = extParams.get(field.getContextKey());
        }
        return Values.exchange(value, DataType.get(field.getDataType()));
        
    }
    
}
