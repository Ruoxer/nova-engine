package com.war3.nova.core.actuator.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Nova;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.actuator.NodeActuator;
import com.war3.nova.core.enumeration.ActuatorType;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.factory.ActuatorFactory;

/**
 * 流程处理器
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午1:30:36
 * @version 1.0
 */
@NovaMapper(enumClass = ActuatorType.class, enumValue = "PROCESS", mapperName = Constants.PROCESS_ACTUATOR)
public class ProcessCoreActuator extends AbstractProcessCoreActuator {

    private final static Logger logger = LoggerFactory.getLogger(ProcessCoreActuator.class);

    @Override
    public Nova doExecute(Nova nova) throws NovaException {
        
        try {
            
            NodeActuator actuator = ActuatorFactory.factory().getNodeActuator(NodeType.get(ProcessConfigContext.getContext().getCurrentNode().getType()));
            logger.info("流程[{}], 流程实例[{}], 节点[{}]调用开始", nova.getProcessId(), nova.getProcessInstId(), nova.getNodeId());
            nova = actuator.execute(nova);
            logger.info("流程[{}], 流程实例[{}], 节点[{}]调用结束", nova.getProcessId(), nova.getProcessInstId(), nova.getNodeId());
                
        } catch (Exception e) {
            throw e;
        }
        
        return nova;
    }

}
