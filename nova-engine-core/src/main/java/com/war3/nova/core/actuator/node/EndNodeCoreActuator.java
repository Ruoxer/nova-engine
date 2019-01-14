package com.war3.nova.core.actuator.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsNode;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.util.Asserts;

/**
 * 结束节点
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午8:51:25
 * @version 1.0
 */
@NovaMapper(enumValue = "END", enumClass = NodeType.class, mapperName = Constants.NODE_ACTUATOR)
public class EndNodeCoreActuator extends AbstractNodeCoreActuator {

    private final static Logger logger = LoggerFactory.getLogger(EndNodeCoreActuator.class);
    
    @Override
    public Nova doExecute(Nova nova) throws NovaException {
        
        logger.info("流程实例[{}], 结束节点执行开始!", nova.getProcessInstId());
        nova.setNodeStatus(ProcessConstants.STATUS_END);
        NvInsNode insNode = this.initAndGetInstance(nova);
        Asserts.assertNull(insNode, "流程实例[{}], 节点[{}]未获得实例化信息!", nova.getProcessInstId(), nova.getNodeId());
        nova.setNodeInstId(insNode.getInstanceId());
        logger.info("流程实例[{}], 结束节点执行结束!", nova.getProcessInstId());
        
        return nova;
    }

}
