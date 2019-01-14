package com.war3.nova.core.engine;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsProcess;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.cache.CacheManager;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.CoreProcessor;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.CoreScheduler;
import com.war3.nova.core.service.util.ExtParameters;
import com.war3.nova.core.service.util.Processes;
import com.war3.nova.core.util.Asserts;
import com.war3.nova.core.util.Dates;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Parameters;
import com.war3.nova.core.util.Strings;

/**
 * 默认引擎实现
 * 
 * @author Cytus_
 * @since 2018年12月13日 下午8:00:29
 * @version 1.0
 */
public class NovaCoreProcessor implements CoreProcessor {
    
    private final static Logger logger = LoggerFactory.getLogger(NovaCoreProcessor.class);
    
    @Autowired
    private CoreScheduler scheduler;

    @Override
    public Nova initProcess(Nova nova) throws NovaException {
        
        Asserts.assertNullOrEmpty(nova.getProcessId(), "尚未传入流程编号!");
        NvProcess nvProcess = CacheManager.getLatestProcessById(nova.getProcessId());
        Asserts.assertNull(nvProcess, "流程[{}]未获取到最新的流程配置信息!", nova.getProcessId());
        NvInsProcess insProcess = new NvInsProcess();
        insProcess.setInstanceId(Strings.getSequence());
        insProcess.setBizSerno(nova.getBizSerno());
        insProcess.setInstOrgId(nova.getInstOrgId());
        insProcess.setMainInstanceId(nova.getMainProcInstId());
        insProcess.setOrgId(nova.getOrgId());
        insProcess.setStartUser(nova.getStartUser());
        insProcess.setStartTime(Dates.formatDateTimeByDef());
        insProcess.setVersion(nvProcess.getVersion());
        insProcess.setProcessId(nova.getProcessId());
        insProcess.setStatus(ProcessConstants.STATUS_RUNNING);
        Processes.initProcessInfo(insProcess);
        if (Novas.nonNullOrEmpty(nova.getExtParams())) {
            Map<String, Object> contextMap = Parameters.paramExchange(nova.getExtParams(), nvProcess.getFields());
            if (Novas.nonNullOrEmpty(contextMap)) {
                ExtParameters.insert(insProcess.getInstanceId(), contextMap);
            }
        }
        nova.setProcessInstId(insProcess.getInstanceId());
        nova.setVersion(nova.getVersion());
        logger.info("流程[{}]实例化成功, 实例号[{}], 版本号[{}]对应的业务流水号为:{}", nvProcess.getId(), insProcess.getInstanceId(), nvProcess.getVersion(), nova.getBizSerno());
        return nova;
    }

    @Override
    public Nova submit(Nova nova) throws NovaException {
        if (Strings.isEmpty(nova.getProcessInstId())) {
            initProcess(nova);
        }
        
        logger.info("流程实例号[{}], 流程[{}]执行提交操作开始", nova.getProcessInstId(), nova.getProcessId());
        NvInsProcess insProcess = Processes.getInitProcessInfo(nova.getProcessInstId());
        
        //判断是否实例化了事件信息，事件实例化应该在调用时已经处理，此处不再处理
        Asserts.assertNull(insProcess, "流程实例[{}]尚未找到对应的实例化流程信息!", nova.getProcessInstId());
        Asserts.assertNull(insProcess.getProcessId(), "流程实例[{}]尚未找到对应的实例化流程信息!", nova.getProcessInstId());
        Asserts.assertEquals(ProcessConstants.STATUS_END, insProcess.getStatus(), "流程实例[{}]已结束!", nova.getProcessInstId());
        
        Processes.toNova(nova, insProcess);
        NvProcess process = CacheManager.getProcessByIdAndVersion(nova.getProcessId(), nova.getVersion());
        ProcessConfigContext.createContext(process);
        
        Map<String, Object> contextMapParams = ExtParameters.queryExtParams(nova.getProcessInstId());
        if (Novas.isNullOrEmpty(contextMapParams)) {
            contextMapParams = Parameters.paramExchange(nova.getExtParams(), process.getFields());
            if (Novas.nonNullOrEmpty(contextMapParams)) {
                ExtParameters.insert(nova.getProcessInstId(), contextMapParams);
            }
        } 
        nova.setExtParams(contextMapParams);
        
        nova = scheduler.dispatch(nova);
        logger.info("流程实例号[{}], 流程[{}]执行提交操作结束", nova.getProcessInstId(), nova.getProcessId());
        return nova;
        
    }

}
