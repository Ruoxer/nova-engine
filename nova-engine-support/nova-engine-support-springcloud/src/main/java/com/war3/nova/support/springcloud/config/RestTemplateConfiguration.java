package com.war3.nova.support.springcloud.config;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.war3.nova.support.springcloud.config.properties.SpringCloudSupportProperties;

/**
 * restTemplate配置
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午1:02:58
 * @version 1.0
 */
@Configuration
public class RestTemplateConfiguration {
    
    @Autowired
    private SpringCloudSupportProperties properties;

    /**
     * spring 非负载均衡启用的resttemplate
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="nova.support.springcloud.loadbalanced", havingValue="false")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
        }
        messageConverters.add(new StringHttpMessageConverter(Charset.forName(properties.getHttp().getCharset())));
        return restTemplate;
    }
    
    /**
     * spring cloud 负载均衡启用的resttemplate
     * @return
     */
    @Bean
    @LoadBalanced
    @ConditionalOnProperty(name="nova.support.springcloud.loadbalanced", havingValue="true")
    public RestTemplate loadBalancedRestTamplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
        }
        messageConverters.add(new StringHttpMessageConverter(Charset.forName(properties.getHttp().getCharset())));
        return restTemplate;
    }
    
    
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }
    
    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(properties.getHttp().getPool().getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(properties.getHttp().getPool().getDefaultMaxPreRoute());
        connectionManager.setValidateAfterInactivity(properties.getHttp().getPool().getValidateAfterInactivity());
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(properties.getHttp().getPool().getSocketTimeout())
                .setConnectTimeout(properties.getHttp().getPool().getConnectTimeout())
                .setConnectionRequestTimeout(properties.getHttp().getPool().getConnectionRequestTimeout())
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}
