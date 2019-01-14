package com.war3.nova.core.factory;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.core.UnsupportArgumentException;
import com.war3.nova.core.actuator.NodeActuator;
import com.war3.nova.core.actuator.ProcessActuator;
import com.war3.nova.core.actuator.TaskActuator;
import com.war3.nova.core.enumeration.ActuatorType;
import com.war3.nova.core.enumeration.NodeType;
import com.war3.nova.core.enumeration.TaskType;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.SpringContexts;


/**
 * 
 * 执行器工厂
 * 
 * @author Cytus_
 * @since 2018年6月26日 下午2:14:20
 * @version 1.0
 *
 */
public class ActuatorFactory {
    
    private final static Logger logger = LoggerFactory.getLogger(ActuatorFactory.class);
    
    private static class ActuatorFactoryHolder {
        private static final ActuatorFactory FACTORY = new ActuatorFactory();
    }
    
    private ActuatorFactory() {} 

    public final static ActuatorFactory factory() {
        return ActuatorFactoryHolder.FACTORY;
    }
    
    /**
     * 获取节点执行器
     * @param nodeType 节点类型
     * @return
     * @throws NovaException
     */
    public NodeActuator getNodeActuator(NodeType nodeType) throws NovaException {
        if (Objects.isNull(nodeType)) {
            String errorMsg = Novas.formatMessage("Unsupported Node Actuator type[{}]!", nodeType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.NODE_ACTUATOR, nodeType);
        NodeActuator nodeActuator = SpringContexts.getBean(bean, NodeActuator.class);
        return nodeActuator;
    }
    
    /**
     * 获取任务执行器
     * @param taskType 任务类型
     * @return
     * @throws NovaException
     */
    public TaskActuator getTaskActuator(TaskType taskType) throws NovaException {
        if (Objects.isNull(taskType)) {
            String errorMsg = Novas.formatMessage("Unsupported Task Actuator type[{}]!", taskType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.TASK_ACTUATOR, taskType);
        TaskActuator routeActuator = SpringContexts.getBean(bean, TaskActuator.class);
        return routeActuator;
    }
    
    /**
     * 流程执行器
     * @param actuatorType 流程执行器类型
     * @return
     * @throws NovaException
     */
    public ProcessActuator getProcessActuator(ActuatorType actuatorType) throws NovaException {
        if (Objects.isNull(actuatorType)) {
            String errorMsg = Novas.formatMessage("Unsupported Process Actuator type[{}]!", actuatorType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.PROCESS_ACTUATOR, actuatorType);
        ProcessActuator CoreActuator = SpringContexts.getBean(bean, ProcessActuator.class);
        return CoreActuator;
    }
    
}
