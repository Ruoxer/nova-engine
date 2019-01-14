package com.war3.nova.core.actuator.node;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Nova;
import com.war3.nova.core.UnsupportOperateException;
import com.war3.nova.core.enumeration.NodeType;

/**
 * 子流程
 * 
 * @author Cytus_
 * @since 2018年12月28日 上午11:28:56
 * @version 1.0
 */
@NovaMapper(enumValue = "SUB", enumClass = NodeType.class, mapperName = Constants.NODE_ACTUATOR)
public class SubProcessNodeCoreActuator extends AbstractNodeCoreActuator {

    @Override
    public Nova doExecute(Nova nova) throws NovaException {
        throw new UnsupportOperateException("当前不支持节点类型!");
    }

}
