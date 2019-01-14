package com.war3.nova.support.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.war3.nova.core.CoreProcessor;
import com.war3.nova.core.CoreScheduler;
import com.war3.nova.core.CoreSelector;
import com.war3.nova.core.DefaultNovaProcessEngine;
import com.war3.nova.core.NovaProcessEngine;
import com.war3.nova.core.actuator.NodeActuator;
import com.war3.nova.core.actuator.ProcessActuator;
import com.war3.nova.core.actuator.TaskActuator;
import com.war3.nova.core.actuator.node.ArtiNodeCoreActuator;
import com.war3.nova.core.actuator.node.AutoNodeCoreActuator;
import com.war3.nova.core.actuator.node.EndNodeCoreActuator;
import com.war3.nova.core.actuator.node.StartNodeCoreActuator;
import com.war3.nova.core.actuator.node.SubProcessNodeCoreActuator;
import com.war3.nova.core.actuator.node.artificialapproval.ArtificialApprovalSubmitService;
import com.war3.nova.core.actuator.node.artificialapproval.CompeteArtiAprvSubmitService;
import com.war3.nova.core.actuator.node.artificialapproval.MultiJoinSignArtiAprvSubmitService;
import com.war3.nova.core.actuator.node.artificialapproval.OtherArtiAprvSubmitService;
import com.war3.nova.core.actuator.process.ProcessCoreActuator;
import com.war3.nova.core.actuator.task.AutoTaskCoreAcutator;
import com.war3.nova.core.calculator.approval.AppointApproverCalculator;
import com.war3.nova.core.calculator.approval.ApproverCalcuator;
import com.war3.nova.core.calculator.approval.ApproverPoolCalculator;
import com.war3.nova.core.calculator.approval.ApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.ApproverSetCalculator;
import com.war3.nova.core.calculator.approval.CompeteApproverCalculator;
import com.war3.nova.core.calculator.approval.MultiJointlySignApproverCalculator;
import com.war3.nova.core.calculator.approval.PoolApproverCalculator;
import com.war3.nova.core.calculator.approval.RandomApproverCalculator;
import com.war3.nova.core.calculator.approval.pool.NovaApproverPoolCalculator;
import com.war3.nova.core.calculator.approval.range.CustomizedApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.range.DepartmentApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.range.OrganizationApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.range.PostApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.range.RelationshipApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.range.RoleApproverRangeCalculator;
import com.war3.nova.core.calculator.approval.range.UserApproverRangeCalculator;
import com.war3.nova.core.calculator.set.IntersectionAprvSetCalculator;
import com.war3.nova.core.calculator.set.UnionApproverSetCalculator;
import com.war3.nova.core.customized.ObjectActuator;
import com.war3.nova.core.customized.actuator.ExpressionObjectActuator;
import com.war3.nova.core.customized.actuator.InvokeClassObjectActuator;
import com.war3.nova.core.customized.actuator.SpringBeanObjectActuator;
import com.war3.nova.core.engine.NovaCoreProcessor;
import com.war3.nova.core.scheduler.SyncProcessScheduler;
import com.war3.nova.core.selector.NodeRouteSelector;
import com.war3.nova.core.selector.TaskRouteSelector;
import com.war3.nova.core.thread.DefaultJavaThreadPool;
import com.war3.nova.core.thread.ThreadPool;
import com.war3.nova.support.springcloud.config.properties.SpringCloudSupportProperties;

/**
 * 
 * 
 * @author Cytus_
 * @since 2018年12月13日 下午5:13:44
 * @version 1.0
 */
@Configuration
public class NovaConfiguation {
    
    @Autowired
    private SpringCloudSupportProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public CoreProcessor novaCoreProcessor() {
        return new NovaCoreProcessor();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="artiNodeCoreActuator")
    public NodeActuator artiNodeCoreActuator() {
        return new ArtiNodeCoreActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="autoNodeCoreActuator")
    public NodeActuator autoNodeCoreActuator() {
        return new AutoNodeCoreActuator();
    }
    
    @Bean 
    @ConditionalOnMissingBean(name="startNodeCoreActuator")
    public NodeActuator startNodeCoreActuator() {
        return new StartNodeCoreActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="endNodeCoreActuator")
    public NodeActuator endNodeCoreActuator() {
        return new EndNodeCoreActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="subProcessNodeCoreActuator")
    public NodeActuator subProcessNodeCoreActuator() {
        return new SubProcessNodeCoreActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="taskRouteSelector")
    public CoreSelector<?> taskRouteSelector() {
        return new TaskRouteSelector();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="nodeRouteSelector")
    public CoreSelector<?> nodeRouteSelector() {
        return new NodeRouteSelector();
    }
    
    @Bean(initMethod="start", destroyMethod="destroy")
    @ConditionalOnMissingBean(name="taskThreadPool")
    public ThreadPool taskThreadPool() {
        DefaultJavaThreadPool pool = new DefaultJavaThreadPool();
        pool.setThreadPoolName(properties.getThread("asyncTask").getThreadPoolName());
        pool.setCorePoolSize(properties.getThread("asyncTask").getCorePoolSize());
        pool.setMaxPoolSize(properties.getThread("asyncTask").getMaxPoolSize());
        pool.setQueueSize(properties.getThread("asyncTask").getWaitQueueSize());
        return pool;
    }
    
    @Bean(initMethod="start", destroyMethod="destroy")
    @ConditionalOnMissingBean(name="asyncProcessThreadPool")
    public ThreadPool asyncProcessThreadPool() {
        DefaultJavaThreadPool pool = new DefaultJavaThreadPool();
        pool.setThreadPoolName(properties.getThread("asyncProcess").getThreadPoolName());
        pool.setCorePoolSize(properties.getThread("asyncProcess").getCorePoolSize());
        pool.setMaxPoolSize(properties.getThread("asyncProcess").getMaxPoolSize());
        pool.setQueueSize(properties.getThread("asyncProcess").getWaitQueueSize());
        return pool;
    }
    
    @Bean
    @ConditionalOnMissingBean(name="processScheduler")
    public CoreScheduler processScheduler() {
        return new SyncProcessScheduler();
    }
    
    @Bean
    @ConditionalOnMissingClass
    public NovaProcessEngine defaultNovaProcessEngine() {
        return new DefaultNovaProcessEngine();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="processCoreActuator")
    public ProcessActuator processCoreActuator() {
        return new ProcessCoreActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="autoTaskActuator")
    public TaskActuator autoTaskActuator() {
        return new AutoTaskCoreAcutator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="unionApproverSetCalculator")
    public ApproverSetCalculator unionApproverSetCalculator() {
        return new UnionApproverSetCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="intersectionAprvSetCalculator")
    public ApproverSetCalculator intersectionAprvSetCalculator() {
        return new IntersectionAprvSetCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="invokeClassObjectActuator")
    @SuppressWarnings("rawtypes")
    public ObjectActuator invokeClassObjectActuator() {
        return new InvokeClassObjectActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="springBeanObjectActuator")
    @SuppressWarnings("rawtypes")
    public ObjectActuator springBeanObjectActuator() {
        return new SpringBeanObjectActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="expressionObjectActuator")
    @SuppressWarnings("rawtypes")
    public ObjectActuator expressionObjectActuator() {
        return new ExpressionObjectActuator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="appointApproverCalculator")
    public ApproverCalcuator appointApproverCalculator() {
        return new AppointApproverCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="poolApproverCalculator")
    public ApproverCalcuator poolApproverCalculator() {
        return new PoolApproverCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="randomApproverCalculator")
    public ApproverCalcuator randomApproverCalculator() {
        return new RandomApproverCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="competeApproverCalculator")
    public ApproverCalcuator competeApproverCalculator() {
        return new CompeteApproverCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="multiJointlySignApproverCalculator")
    public ApproverCalcuator multiJointlySignApproverCalculator() {
        return new MultiJointlySignApproverCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="customizedApproverRAngeCalcuator")
    public ApproverRangeCalculator customizedApproverRAngeCalcuator() {
        return new CustomizedApproverRangeCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="postApproverRangeCalculator")
    public ApproverRangeCalculator postApproverRangeCalculator() {
        return new PostApproverRangeCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="userApproverRangeCalculator")
    public ApproverRangeCalculator userApproverRangeCalculator() {
        return new UserApproverRangeCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="departmentApproverRangeCalculator")
    public ApproverRangeCalculator departmentApproverRangeCalculator() {
        return new DepartmentApproverRangeCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="organizationApproverRangeCalculator")
    public ApproverRangeCalculator organizationApproverRangeCalculator() {
        return new OrganizationApproverRangeCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="relationshipApproverRangeCalculator")
    public ApproverRangeCalculator relationshipApproverRangeCalculator() {
        return new RelationshipApproverRangeCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="roleApproverRangeCalculator")
    public ApproverRangeCalculator roleApproverRangeCalculator() {
        return new RoleApproverRangeCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public ApproverPoolCalculator defaultApproverPoolCalculator() {
        return new NovaApproverPoolCalculator();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="multiJoinSignArtiAprvSubmitService")
    public ArtificialApprovalSubmitService multiJoinSignArtiAprvSubmitService() {
        return new MultiJoinSignArtiAprvSubmitService();
    }
    
    @Bean
    @ConditionalOnMissingBean(name="competeArtiAprvSubmitService")
    public ArtificialApprovalSubmitService competeArtiAprvSubmitService() {
        return new CompeteArtiAprvSubmitService();
    }
    
    @Bean
    public ArtificialApprovalSubmitService otherArtiAprvSubmitService() {
        return new OtherArtiAprvSubmitService();
    }
}
