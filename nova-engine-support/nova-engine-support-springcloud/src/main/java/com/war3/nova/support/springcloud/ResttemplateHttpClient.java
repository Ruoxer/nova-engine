package com.war3.nova.support.springcloud;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.war3.nova.core.UnsupportArgumentException;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Strings;
import com.war3.nova.support.springcloud.config.properties.SpringCloudSupportProperties;

/**
 * resttemplate模式下的http调用
 * 
 * @author Cytus_
 * @since 2018年12月21日 上午10:32:12
 * @version 1.0
 */
@Service
public class ResttemplateHttpClient {
    
    private final static Logger logger = LoggerFactory.getLogger(ResttemplateHttpClient.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private SpringCloudSupportProperties properties;
    
    public String postCall(String url, String contentType, Map<String, Object> requestParams) throws Exception {
        
        
        if (Strings.isEmpty(url)) {
            logger.error("当前调用的url为空!");
            throw new UnsupportArgumentException("当前调用的url为空!");
        }
        
        HttpHeaders httpHeaders = new HttpHeaders();
        if (Strings.isEmpty(contentType)) {
            contentType = properties.getHttp().getContentType();
        }
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, contentType);
        httpHeaders.add(HttpHeaders.CONNECTION, "keep-alive");
        
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestParams, httpHeaders);
        
        try {
            
            logger.info("URL[{}]调用开始", url);
            ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            if (!respEntity.getStatusCode().is2xxSuccessful()) {
                String errorMsg = Novas.formatMessage("当前url调用失败, 返回状态为:{} {}"+ respEntity.getStatusCode().value(), respEntity.getStatusCode().getReasonPhrase());
                logger.error(errorMsg);
                throw new Exception(errorMsg);
            }
            logger.info("URL[{}]调用结束", url);
            return respEntity.getBody();
        } catch (Exception e) {
            logger.error("当前url调用失败! cause by:{}", e.getMessage());
            throw e;
        }
    }


}
