package com.war3.nova.support.springcloud;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.Result;
import com.war3.nova.core.customized.ObjectResult;
import com.war3.nova.core.customized.task.CustomizedTask;
import com.war3.nova.core.customized.task.TaskParameter;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Strings;

/**
 * 
 * 自定义的taask调用
 * 
 * @author Cytus_
 * @since 2018年12月21日 上午10:37:10
 * @version 1.0
 */
@Service
public class CustomizedSpringCloudTask implements CustomizedTask {
    
    private final static Logger logger = LoggerFactory.getLogger(CustomizedSpringCloudTask.class);
    
    @Autowired
    private ResttemplateHttpClient httpClient;
    
    @Override
    public Result<?> execute(TaskParameter taskParameter) throws NovaException {
        
        
        String url = (String) taskParameter.getExtParams().get("url");
        
        if (Strings.isEmpty(url)) {
            logger.error("当前任务配置的url为空!");
            return ObjectResult.createFailure(Constants.SYSTEM_ERROR_CODE, "当前任务配置的url为空!");
        }
        
        Map<String, Object> extMap = Novas.removeKeys(taskParameter.getExtParams(), "Content-Type", "url");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("bizSerno", taskParameter.getBizSerno());
        dataMap.put("extParams", extMap);
        logger.info("当前调用的url[{}], 传入的参数为:{}", url, dataMap);
        try {
            String returnString = httpClient.postCall(url, (String) taskParameter.getExtParams().get("Content-Type"), dataMap);
            logger.info("当前调用的url[{}], 返回的信息为:{}", url, returnString);
            return ObjectResult.createSuccess(returnString);
        } catch (Exception e) {
            String errorMsg = Novas.formatMessage("当前URL[{}], 调用失败! cause by:{}", url, e.getMessage());
            logger.error(errorMsg, e);
            return ObjectResult.createFailure(Constants.SYSTEM_ERROR_CODE, errorMsg);
        }
        
    }

}
