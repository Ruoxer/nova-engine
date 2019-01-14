package com.war3.nova.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * 映射工具类
 *
 * @author Cytus_
 * @since 2018年6月9日 上午10:50:42
 * @version 1.0
 *
 */
public class Reflects {

    public final static Object convert(Object object, Class<?> convertClass) throws Exception {
        Object rtnObject = null;
        if (Objects.nonNull(convertClass) && Objects.nonNull(object)) {

            if (Objects.equals(convertClass, object.getClass())) {
                rtnObject = object;
            }

            String strObject = String.valueOf(object);
            if (String.class.isAssignableFrom(convertClass)) {
                rtnObject = strObject;
            }

            if (Number.class.isAssignableFrom(convertClass)) {
                Method method = getObjectMethod(convertClass, "valueOf", String.class);
                if (Objects.nonNull(method)) {
                    rtnObject = method.invoke(convertClass.newInstance(), strObject);
                } else {
                    method = getObjectMethod(convertClass, "valueOf", Long.class);
                    if (Objects.nonNull(method)) {
                        rtnObject = method.invoke(convertClass.newInstance(), Long.valueOf(strObject));
                    }
                }
            }
        }

        return rtnObject;
    }

    public final static Field getObjectFiled(Object bean, String name) throws Exception {
        Class<? extends Object> clazz = bean.getClass();
        return getObjectFiled(clazz, name);
    }

    public final static Field getObjectFiled(Class<?> clazz, String name) throws Exception {
        while (Objects.nonNull(clazz) && !clazz.getName().equals("java.lang.Object")) {
            Field field = clazz.getDeclaredField(name);
            if (Objects.nonNull(field)) {
                return field;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    /**
     * 设置对象值
     * @param object
     * @param field
     * @param value
     */
    public final static void setObjectFieldValue(Object object, Field field, Object value) {
        boolean isAccessible = field.isAccessible();
        try {
            if (!isAccessible) {
                field.setAccessible(true);
            }
            field.set(object, value);
        } catch (Exception e){

        } finally {
            if (!isAccessible) {
                field.setAccessible(false);
            }
        }

    }

    public final static Object getObjectFieldValue(Object object, Field field) {
        boolean isAccessible = field.isAccessible();
        try {
            if (!isAccessible) {
                field.setAccessible(true);
            }
            return field.get(object);
        } catch (Exception e){

        } finally {
            if (!isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public final static Method getObjectMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws Exception {
        return Optional.of(clazz.getDeclaredMethod(name, parameterTypes)).orElse(clazz.getMethod(name, parameterTypes));
    }

}
