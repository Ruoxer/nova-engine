package com.war3.nova.core.util;

import java.util.Objects;

import com.war3.nova.core.enumeration.DataType;

/**
 * 值操作工具类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午3:02:18
 * @version 1.0
 */
public class Values {
    
    @SuppressWarnings("unchecked")
    public final static <T> T exchange(Object object, DataType dataType) {
        
        if (Objects.nonNull(object)) {
            Object value = object;
            switch (dataType) {
                case BOOLEAN : value = Boolean.valueOf(Strings.replaceNullByObj(object)); break;
                case DATE : value = Dates.parseDateByDef(Strings.replaceNullByObj(object)); break;
                case DATETIME : value = Dates.parseDateTimeByDef(Strings.replaceNullByObj(object)); break;
                case DECIMAL : value = Numbers.toBigDecimal(Strings.replaceNullByObj(object)); break;
                case DOUBLE : value = Numbers.toDouble(object); break;
                case FLOAT : value = Numbers.toFloat(object); break;
                case INTEGER : value = Numbers.toInteger(object); break;
                case STRING : value = Strings.replaceNullByObj(object); break;
                case TIME : value = Dates.parseDate(Strings.replaceNullByObj(object), Dates.PATTERN_TIME); break;
            }
            return (T) value;
        }
        return null;
        
    }

}
