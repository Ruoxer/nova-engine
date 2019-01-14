package com.war3.nova.core.selector;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NVRuntimeException;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsApproval;
import com.war3.nova.beans.NvInsNode;
import com.war3.nova.beans.NvNode;
import com.war3.nova.beans.NvProcess;
import com.war3.nova.beans.NvRoute;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.CoreSelector;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.customized.route.RouteParameter;
import com.war3.nova.core.customized.route.RouteRunner;
import com.war3.nova.core.enumeration.ExecutionMode;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.enumeration.RouteConditionType;
import com.war3.nova.core.enumeration.RouteType;
import com.war3.nova.core.service.util.Approvals;
import com.war3.nova.core.service.util.Nodes;
import com.war3.nova.core.util.Asserts;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Parameters;
import com.war3.nova.core.util.Strings;

/**
 * 
 * 节点路由选择器
 * 
 * @author Cytus_
 * @since 2018年6月26日 下午8:55:29
 * @version 1.0
 *
 */
@NovaMapper(enumClass = RouteType.class, enumValue = "NODE", mapperName = Constants.ROUTE_SELECTOR)
public class NodeRouteSelector implements CoreSelector<NvNode> {
    
    private final static Logger logger = LoggerFactory.getLogger(NodeRouteSelector.class);
    
    @Override
    public NvNode select(Nova nova) throws NovaException {
        
        //初始化第一个节点，即开始节点
        if (Strings.isEmpty(nova.getNodeId())) {
            NvInsNode insNode = Nodes.getCurrentNode(nova.getProcessInstId());
            
            if (Objects.isNull(insNode) || Strings.isEmpty(insNode.getInstanceId())) {
                logger.info("当前流程[{}]为寻找开始节点", nova.getProcessId());
                ProcessConfigContext ctx = ProcessConfigContext.getContext();
                Map<String, NvNode> nvNodes = ctx.getProcess().getNode();
                List<NvNode> startNodes = nvNodes.values().parallelStream().filter(s -> NodeType.compare(NodeType.START, s.getType())).collect(Collectors.toList());
                if (Novas.isNullOrEmpty(startNodes) || startNodes.size() > 1) {
                    String errorMsg = Novas.formatMessage("当前流程[{}]不存在开始节点或者存在多个开始节点!", nova.getProcessId());
                    logger.error(errorMsg);
                    throw new NovaException(errorMsg);
                }
                return startNodes.get(0);
                
            } else {
                // 当前节点执行完成
                nova.setNodeId(insNode.getNodeId());
                nova.setNodeStatus(insNode.getStatus());
                if (ProcessConstants.STATUS_END.equals(nova.getNodeStatus())) {
                    logger.info("当前流程实例[{}]已实例化, 并且当前节点[{}]已执行完成, 寻找下一节点", nova.getProcessInstId(), nova.getNodeId());
                    // 获取当前节点的下一节点
                    return selectNextNode(nova, insNode.getNodeId());
                } 
                
                logger.info("当前流程实例[{}]已实例化, 并且当前节点[{}]未执行完成, 状态为[{}], 返回当前节点", nova.getProcessInstId(), nova.getNodeId(), nova.getNodeStatus());
                // 未执行完成获取当前节点    
                return ProcessConfigContext.getContext().getProcess().getNode(insNode.getNodeId());
            }
        } 
        // 存在当前执行节点
        else {
            
            // 当前节点执行完成
            if (ProcessConstants.STATUS_END.equals(nova.getNodeStatus())) {
                if (Strings.isNotEmpty(nova.getNextNodeId())) {
                    logger.info("流程实例[{}], 当前节点[{}]已执行完成, 传入的下一待执行节点为[{}]", nova.getProcessInstId(), nova.getNodeId(), nova.getNextNodeId());
                    //assertNodeRelation(nova.getNodeId(), nova.getNextNodeId());
                    return ProcessConfigContext.getContext().getProcess().getNode(nova.getNextNodeId());
                } else {
                    logger.info("流程实例[{}], 当前节点[{}]已执行完成, 无传入的下一待执行节点, 当前进行查找下一节点", nova.getProcessInstId(), nova.getNodeId());
                    return selectNextNode(nova, nova.getNodeId());
                }
            } 
            
            // 拒绝  查询结束节点
            if (ProcessConstants.STATUS_REFUSE.equals(nova.getNodeStatus())) {
                logger.info("流程实例[{}], 当前节点[{}]已拒绝, 查找结束节点", nova.getProcessInstId(), nova.getNodeId());
                return findEndNode();
            }
            
            else {
                logger.info("流程实例[{}], 当前节点[{}]未执行完成, 返回当前节点信息", nova.getProcessInstId(), nova.getNodeId());
                return ProcessConfigContext.getContext().getProcess().getNode(nova.getNodeId());
                
            }
        }
    }
    
    /**
     * 针对指定节点，判断配置的关联关系
     * @param srcNodeId
     * @param targetNodeId
     * @throws NovaException
     */
    protected void assertNodeRelation(String srcNodeId, String targetNodeId) throws NovaException {
        List<NvRoute> routes = ProcessConfigContext.getContext().getProcess().getRoute(srcNodeId);
        Asserts.assertCollectionNullOrEmpty(routes, "源节点[{}]未配置路由", srcNodeId);
        long count = routes.parallelStream().filter(s -> s.getTargetRef().equals(targetNodeId)).count();
        Asserts.assertMinNumber(count, 0, "源节点[{}], 目标节点[{}], 不存在路由关系", srcNodeId, targetNodeId);
    }
    
    /**
     * 查询结束节点
     * @return
     */
    protected NvNode findEndNode() {
        NvProcess process = ProcessConfigContext.getContext().getProcess();
        NvNode node = process.getNode().values().parallelStream().filter(s -> NodeType.compare(NodeType.END, s.getType())).findFirst().orElse(null);
        if (Objects.isNull(node)) {
            throw new NVRuntimeException("流程["+ process.getId() +"]未配置结束节点!");
        }
        return node;
    }

    /**
     * 根据当前节点获取下一节点
     * @param nova
     * @param nodeId
     * @return
     */
    protected NvNode selectNextNode(Nova nova, String nodeId) {
        
        List<NvRoute> routes = ProcessConfigContext.getContext().getProcess().getRoute(nodeId);
        if (Novas.isNullOrEmpty(routes)) return null;
        
        NvRoute nvRoute = null;
        List<NvInsApproval> approvals = null;
        if (routes.size() > 1) {
            approvals = Approvals.getNodeAllApproval(nova.getNodeInstId());
        }
        
        for (NvRoute route : routes) {
            if (nvRoute == null && RouteConditionType.compare(RouteConditionType.DEFAULT, route.getType())) {
                logger.info("流程实例[{}], 当前节点[{}], 路由[{}]为默认路由", nova.getProcessInstId(), nova.getNodeId(), route.getId());
                nvRoute = route;
            } else if (RouteConditionType.compare(RouteConditionType.CONDITION, route.getType())) {
                RouteParameter parameter = Parameters.createRouteParameter(nova, approvals, route.getFields());
                boolean result = RouteRunner.executeRoute(ExecutionMode.get(route.getConditionExecutionMode()), route.getCondition(), parameter);
                logger.info("流程实例[{}], 当前节点[{}], 路由[{}]执行结果为:{}", nova.getProcessInstId(), nova.getNodeId(), route.getId(), result);
                if (result) {
                    nvRoute = route;
                    break;
                }
            } else {
                logger.warn("流程实例[{}], 当前节点[{}], 存在多个默认路由");
            }
        }
        
        if (Objects.nonNull(nvRoute)) {
            logger.info("流程实例[{}], 当前节点[{}], 查找的下一路由为:RouteID[{}], RouteName[{}]", nova.getProcessInstId(), nova.getNodeId(), nvRoute.getId(), nvRoute.getName());
            logger.info("流程实例[{}], 当前节点[{}]通过路由条件查找的下一节点为[{}]", nova.getProcessInstId(), nova.getNodeId(), nvRoute.getTargetRef());
            return ProcessConfigContext.getContext().getProcess().getNode(nvRoute.getTargetRef());
        }
        logger.info("流程实例[{}], 当前节点[{}]通过路由条件查找的下一节点为空", nova.getProcessInstId(), nova.getNodeId());
        return null;
        
    }


}
