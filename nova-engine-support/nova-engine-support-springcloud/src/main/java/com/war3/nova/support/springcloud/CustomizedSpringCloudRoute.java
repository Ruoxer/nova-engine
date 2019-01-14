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
import com.war3.nova.core.customized.BooleanResult;
import com.war3.nova.core.customized.ObjectResult;
import com.war3.nova.core.customized.route.CustomizedRoute;
import com.war3.nova.core.customized.route.RouteParameter;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Strings;

/**
 * 自定义路由
 * 
 * @author Cytus_
 * @since 2018年12月21日 上午10:53:18
 * @version 1.0
 */
@Service
public class CustomizedSpringCloudRoute implements CustomizedRoute {

    private final static Logger logger = LoggerFactory.getLogger(CustomizedSpringCloudRoute.class);
    
    @Autowired
    private ResttemplateHttpClient httpClient;
    
    @Override
    public Result<?> execute(RouteParameter routeParameter) throws NovaException {
        
        
        String url = (String) routeParameter.getExtParams().get("url");
        
        if (Strings.isEmpty(url)) {
            logger.error("当前任务配置的url为空!");
            return ObjectResult.createFailure(Constants.SYSTEM_ERROR_CODE, "当前任务配置的url为空!");
        }
        
        Map<String, Object> extMap = Novas.removeKeys(routeParameter.getExtParams(), "Content-Type", "url");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("bizSerno", routeParameter.getBizSerno());
        dataMap.put("extParams", extMap);
        logger.info("当前调用的url[{}], 传入的参数为:{}", url, dataMap);
        try {
            String returnString = httpClient.postCall(url, (String) routeParameter.getExtParams().get("Content-Type"), dataMap);
            logger.info("当前调用的url[{}], 返回的信息为:{}", url, returnString);
            return BooleanResult.createSuccess(Boolean.valueOf(returnString));
        } catch (Exception e) {
            String errorMsg = Novas.formatMessage("当前URL[{}], 调用失败! cause by:{}", url, e.getMessage());
            logger.error(errorMsg, e);
            return ObjectResult.createFailure(Constants.SYSTEM_ERROR_CODE, errorMsg);
        }
        
    }

}
