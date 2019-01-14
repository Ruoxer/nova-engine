package com.war3.nova.core.selector;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvInsTask;
import com.war3.nova.beans.NvNode;
import com.war3.nova.beans.NvTask;
import com.war3.nova.core.ProcessConfigContext;
import com.war3.nova.core.CoreSelector;
import com.war3.nova.core.ProcessConstants;
import com.war3.nova.core.enumeration.RouteType;
import com.war3.nova.core.service.util.Tasks;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.Strings;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月15日 下午3:04:45
 * @version 1.0
 */
@NovaMapper(enumClass = RouteType.class, enumValue = "TASK", mapperName = Constants.ROUTE_SELECTOR)
public class TaskRouteSelector implements CoreSelector<String>{

    private final static Logger logger = LoggerFactory.getLogger(TaskRouteSelector.class);
    
    @Override
    public String select(Nova nova) throws NovaException {
        logger.info("节点实例[{}], 节点[{}], 当前执行任务[{}]查询下一执行任务开始", nova.getNodeInstId(), nova.getNodeId(), nova.getTaskId());
        String nextTaskId = this.doSelect(nova);
        logger.info("节点实例[{}], 节点[{}], 当前执行任务[{}], 下一待执行任务为[{}]", nova.getNodeInstId(), nova.getNodeId(), nova.getTaskId(), nextTaskId);
        return nextTaskId;
    }
    
    public String doSelect(Nova nova) throws NovaException {
        
        if (Strings.isEmpty(nova.getTaskId())) {
            List<NvInsTask> insTasks = Tasks.queryAllByNodeInstanceId(nova.getNodeInstId());
            if (Novas.isNullOrEmpty(insTasks)) {
                NvTask task = findStartTask();
                return Objects.nonNull(task) ? task.getId() : null;
            } else {
                NvInsTask lastInsTask = findLastExeTask(insTasks);
                if (!ProcessConstants.STATUS_END.equals(lastInsTask.getStatus())) {
                    return lastInsTask.getTaskId();
                } else {
                    return findNextTask(ProcessConfigContext.getContext().getCurrentNode().getTask(lastInsTask.getTaskId()));
                }
            }
        } else {
            return findNextTask(ProcessConfigContext.getContext().getCurrentNode().getTask(nova.getTaskId()));
        }
    }
    
    /**
     * 查找下一个任务
     * @param task
     * @return
     */
    protected String findNextTask(NvTask task) {
        int order = task.getOrder();
        Map<String, NvTask> tasks = ProcessConfigContext.getContext().getCurrentNode().getTask();
        NvTask nextTask = tasks.values().stream().filter(s -> (order + 1) == s.getOrder()).findFirst().orElse(null);
        return Objects.nonNull(nextTask) ? nextTask.getId() : null;
    }

    /**
     * 查找开始任务
     * @return
     */
    protected NvTask findStartTask() {
        NvNode node = ProcessConfigContext.getContext().getCurrentNode();
        Collection<NvTask> task = node.getTask().values();
        return task.stream().sorted(new Comparator<NvTask>() {

            @Override
            public int compare(NvTask o1, NvTask o2) {
                return Integer.valueOf(o1.getOrder()).compareTo(o2.getOrder());
            }
        }).findFirst().orElse(null);
    }
    
    /**
     * 查找最后一个执行的任务
     * @param insTasks
     * @return
     */
    protected NvInsTask findLastExeTask(List<NvInsTask> insTasks) {
        return insTasks.stream().sorted(new Comparator<NvInsTask>() {

            @Override
            public int compare(NvInsTask o1, NvInsTask o2) {
                return Integer.valueOf(o2.getOrder()).compareTo(o1.getOrder());
            }
        }).findFirst().orElse(null);
    }

    
}
