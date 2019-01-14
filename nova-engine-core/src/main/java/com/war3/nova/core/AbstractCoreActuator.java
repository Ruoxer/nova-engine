package com.war3.nova.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;
import com.war3.nova.core.enumeration.ListenerStage;
import com.war3.nova.core.util.Novas;

/**
 * 
 * @param <T>
 * @author Cytus_
 * @since 2018年12月18日 上午8:28:13
 * @version 1.0
 */
public abstract class AbstractCoreActuator<T> implements CoreActuator {
    
    private final static Logger logger = LoggerFactory.getLogger(AbstractCoreActuator.class);

    /**
     * 获取不同的监听对象向下的传值
     * @param nova
     * @param e
     * @return
     */
    public abstract T getListenerDelegate(Nova nova, Exception e);
    
    public Nova execute(Nova nova) throws NovaException {
        List<? extends CoreListener<T>> listeners = this.getListeners();
        
        try {
            executeListener(listeners, nova, ListenerStage.BEFORE, null);
            nova = doExecute(nova);
        } catch (NovaException e) { 
            executeListener(listeners, nova, ListenerStage.EXCEPTION, e);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            executeListener(listeners, nova, ListenerStage.EXCEPTION, e);
            throw new NovaException(e.getMessage(), e);
        } 
        executeListener(listeners, nova, ListenerStage.AFTER, null);
        
        return nova;
    }
    
    /**
     * 实际每个执行器需要做的执行动作
     * @param nova
     * @return
     * @throws NovaException
     */
    public abstract Nova doExecute(Nova nova) throws NovaException;
    
    /**
     * 获得配置的监听器
     * @return
     * @throws NovaException
     */
    public abstract List<? extends CoreListener<T>> getListeners() throws NovaException;
    
    /**
     * 处理监听器
     * @param listeners
     * @param nova
     * @param stage
     * @param e
     */
    protected void executeListener(List<? extends CoreListener<T>> listeners, Nova nova, ListenerStage stage, Exception e) {
        if (Novas.nonNullOrEmpty(listeners)) {
            T t = getListenerDelegate(nova, e);
            for (int i = 0; i < listeners.size(); i++) {
                String lisenerName = listeners.get(i).getClass().getName();
                logger.info("监听器:[{}], 环节[{}], 调用开始", lisenerName, stage);
                switch (stage) {
                    case BEFORE : listeners.get(i).beforeNotify(t); break;
                    case AFTER : listeners.get(i).afterNotify(t); break;
                    case EXCEPTION : listeners.get(i).exceptionNotify(t); break;
                    default : break;
                }
                logger.info("监听器:[{}], 环节[{}], 调用结束", lisenerName, stage);
            }
        }
    }

}
