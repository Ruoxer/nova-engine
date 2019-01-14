package com.war3.nova.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;


/**
 * java bean的一些操作工具类
 * @author Cytus_
 * @since 2018-08-28 10:12:06
 * @version 1.0
 */
public final class Beans {

    /**
     * 将javabean转换成Map对象，使用cglib的BeanMap进行转换
     * @param bean
     * @return
     */
    public static Map<String, Object> bean2MapByBeanMap(Object bean) {
        if (bean == null) {
            return Collections.emptyMap();
        }
        try {
            Map<String, Object> rtnMap = new HashMap<String, Object>();
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                Object value = beanMap.get(key);
                if (Objects.nonNull(value)) {
                    if (value instanceof String || value instanceof Number) {
                        rtnMap.put(key +"", value);
                    } else if (value instanceof List) {
                        rtnMap.put(key +"", listBean2ListByBeanMap(value));
                    } else {
                        rtnMap.put(key +"", bean2MapByBeanMap(value));
                    }
                }
            }
            return rtnMap;
        } catch (Exception e) {
            throw new IllegalArgumentException("bean2Map fail: " + e.getMessage(), e);
        }
    }
    
    /**
     * 将list bean对象转换成List<Map>对象 与{@link bean2MapByBeanMap}方法配合使用，使用cglib的BeanMap进行转换
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> listBean2ListByBeanMap(Object obj) {
        List<Object> list = (List<Object>) obj;
        List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
        for (Object listObj : list) {
            Map<String, Object> beanMap = bean2MapByBeanMap(listObj);
            listMap.add(beanMap);
        }
        return listMap;
    }
    

    /**
     * list对象转成list bean
     * @param list
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T list2BeanList(List<Map<String, Object>> list, Class<?> clazz) throws Exception {
        List<?> bean = new ArrayList();
        for (Map<String, Object> dataMap : list) {
            Object object = clazz.newInstance();
            bean.add(map2Bean(dataMap, object));
        }
        return (T) bean;
    }
    
    /**
     * map转bean   目前List暂无处理
     * @param dataMap
     * @param bean
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T map2Bean(Map<String, Object> dataMap, Object bean) {
        if (Objects.isNull(bean)) {
            throw new NullPointerException("exchange object must not null!");
        }
        
        if (Objects.isNull(dataMap) || dataMap.isEmpty()) {
            return (T) bean;
        }
        
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (dataMap.containsKey(key) && !"class".equals(key)) {
                    Object value = dataMap.get(key);
                    if (value instanceof String || value instanceof Number) {
                        property.getWriteMethod().invoke(bean, value);
                    } else if (value instanceof List) {
                        //property.getWriteMethod().invoke(bean, list2BeanList(value, value.getClass().));
                    } else {
                        Class clazz = property.getPropertyType();      
                        Object obj = clazz.newInstance();
                        Map<String, Object> beanMap = (Map<String, Object>) value;
                        obj = map2Bean(beanMap, obj);
                        property.getWriteMethod().invoke(bean, obj);
                    }
                }
            }
            return (T) bean;
        } catch (Exception e) {
            throw new IllegalArgumentException("map2Bean fail: " + e.getMessage(), e);
        }
    }
    
    /**
     * 将list bean对象转换成List< Map >对象 与{@link bean2Map}方法配合使用，使用jdk自带的的BeanInfo进行转换
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> listBean2List(Object obj) {
        List<Object> list = (List<Object>) obj;
        List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
        for (Object listObj : list) {
            Map<String, Object> beanMap = bean2Map(listObj);
            listMap.add(beanMap);
        }
        return listMap;
    }
    
    /**
     * 将javabean转换成Map对象，使用jdk自带的的BeanInfo进行转换
     * @param bean
     * @return
     */
    public static Map<String, Object> bean2Map(Object bean) {
        if (bean == null) {
            return Collections.emptyMap();
        }
        
        try {
            Map<String, Object> beanMap = new HashMap<String, Object>();
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                
                if (!key.equals("class")) {
                    Object value = property.getReadMethod().invoke(bean);
                    
                    if (property.getPropertyType().isAssignableFrom(String.class) || property.getPropertyType().isAssignableFrom(Number.class)) {
                        beanMap.put(key, value);
                    } else if (value instanceof List) {
                        beanMap.put(key, listBean2List(value));
                    } else {
                        beanMap.put(key, bean2Map(value));
                    }
                }
                
            }
            return beanMap;
            
        } catch (Exception e) {
            throw new IllegalArgumentException("bean2Map fail: " + e.getMessage(), e);
        }
    }
    
    /**
     * bean copy
     * @param srcBean
     * @param targetBean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T beanCopy(Object srcBean, Object targetBean) {
        if (Objects.nonNull(targetBean) && Objects.nonNull(srcBean)) {
            BeanCopier beanCopier = BeanCopier.create(srcBean.getClass(), targetBean.getClass(), false);
            beanCopier.copy(srcBean, targetBean, null);
            return (T) targetBean;
        }
        return null;
    }
    
    public static <T> T beanCopy(Object srcBean, Class<T> clazz) {
        return beanCopy(srcBean, newInstance(clazz));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> Collection<? extends Object> beansCopy(Collection<? extends Object> srcBeans, Class<?> targetBean) throws Exception {
        if (Objects.isNull(srcBeans) || Objects.isNull(targetBean)) return null;
        if (srcBeans.isEmpty()) return Collections.emptyList();
        BeanCopier beanCopier = BeanCopier.create(srcBeans.iterator().next().getClass(), targetBean, false);
        List<T> list = new ArrayList<T>(srcBeans.size());
        srcBeans.forEach(s -> {
            T t = (T) newInstance(targetBean);
            beanCopier.copy(s, t, null);
            list.add(t);
        });
        return list;
    }
    
    public static final <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            LoggerFactory.getLogger(Beans.class).error("Class类对象:"+ clazz.getName() +"实例化时出现异常!", e);
        }
        return null;
    }
    
    /**
     * 从bean中获取某一属性值
     * @param object
     * @param name
     * @return
     * @throws Exception
     */
    public static Object getProptery(Object object, String name) throws Exception {
        String clazz = object.getClass().getName();
        if (clazz.matches(".+Map$")) {
            return object.getClass().getMethod("get", Object.class).invoke(object, name);
        } 
            
        if (object instanceof Collection) {
            return null;
        } 
        
        if (object instanceof String || object instanceof Number) {
            return object;
        } 
        
        Field field = Reflects.getObjectFiled(object, name);
        if (Objects.nonNull(field)) {
            return Reflects.getObjectFieldValue(object, field);
        }
        return null;
    }
    
    /**
     * 设置
     * @param bean
     * @param name
     * @param value
     * @throws Exception
     */
    public final static void setProperty(Object bean, String name, Object value) throws Exception {
        Field field = Reflects.getObjectFiled(bean, name);
        if (Objects.nonNull(field)) {
            Reflects.setObjectFieldValue(bean, field, Reflects.convert(value, field.getType()));
        }
    }
    
    
    
}
