package com.war3.nova.core.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvField;
import com.war3.nova.beans.NvInsApproval;
import com.war3.nova.core.customized.route.RouteParameter;
import com.war3.nova.core.customized.task.TaskParameter;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月26日 上午11:03:21
 * @version 1.0
 */
public final class Parameters {

    
    public final static TaskParameter createTaskParameter(Nova nova, List<NvField> fields) {
        TaskParameter param = new TaskParameter();
        param.setBizSerno(nova.getBizSerno());
        Map<String, Object> extMap = paramExchange(nova.getExtParams(), fields);
        param.setExtParams(extMap);
        return param;
    }
    
    public final static RouteParameter createRouteParameter(Nova nova, List<NvInsApproval> insApprovals, List<NvField> fields) {
        RouteParameter param = new RouteParameter();
        param.setBizSerno(nova.getBizSerno());
        param.setApprovals(insApprovals);
        param.setNodeId(nova.getNodeId());
        param.setProcessId(nova.getProcessId());
        Map<String, Object> extMap = paramExchange(nova.getExtParams(), fields);
        param.setExtParams(extMap);
        return param;
    }
    
    public final static Map<String, Object> paramExchange(Map<String, Object> extParams, List<NvField> fields) {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();
        if (Novas.nonNullOrEmpty(fields)) {
            Map<String, Object> customParams = Fields.toCustomizedParameter(fields, extParams);
            if (Novas.nonNull(customParams)) {
                dataMap.putAll(customParams);
            }
        }
        return dataMap;
        
    }
    
    
}
