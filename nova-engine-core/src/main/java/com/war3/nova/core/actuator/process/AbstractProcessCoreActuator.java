package com.war3.nova.core.actuator.process;

import java.util.List;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.core.AbstractCoreActuator;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.actuator.ProcessActuator;
import com.war3.nova.core.CoreListener;
import com.war3.nova.core.listener.DelegateProcess;
import com.war3.nova.core.util.Listeners;

/**
 * 流程核心处理抽象类
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午2:36:49
 * @version 1.0
 */
public abstract class AbstractProcessCoreActuator extends AbstractCoreActuator<DelegateProcess> implements ProcessActuator {
    
    
    @Override
    public DelegateProcess getListenerDelegate(Nova nova, Exception e) {
        return Listeners.createProcessDelegate(nova, e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<? extends CoreListener<DelegateProcess>> getListeners() throws NovaException {
        NvProcess process = ProcessConfigContext.getContext().getProcess();
        return (List<? extends CoreListener<DelegateProcess>>) Listeners.initListener(process.getListeners());
    }

}
