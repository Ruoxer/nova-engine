package com.war3.nova.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 流程上下文执行对象
 * 
 * @author Cytus_
 * @since 2018年12月17日 上午10:18:44
 * @version 1.0
 */
public class Nova extends ToString implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 流程id
    private String processId;
    // 流程实例ID
    private String processInstId;
    // 主流程实例id
    private String mainProcInstId;
    // 当前节点状态
    private String processStatus;
    // 版本号
    private String version;
    // 当前节点ID
    private String nodeId;
    // 当前节点实例Id
    private String nodeInstId;
    // 上一节点Id
    private String preNodeId;
    // 下一节点Id
    private String nextNodeId;
    // 当前节点状态
    private String nodeStatus;
    // 审批意见
    private NvInsApproval instApproval;
    // 下一节点审批人员
    private List<Approver> nextApprovers;
    // 业务流水号
    private String bizSerno;
    // 任务ID
    private String taskId;
    // 上一任务ID
    private String preTaskId;
    // 任务状态
    private String taskStatus;
    // 全局业务流水号
    private String globalBizSerno;
    // 发起机构编号
    private String orgId;
    // 发起法人机构编号
    private String instOrgId;
    // 发起者
    private String startUser;
    // 扩展参数对象集合
    private Map<String, Object> extParams;
    
    public String getGlobalBizSerno() {
        return globalBizSerno;
    }

    public void setGlobalBizSerno(String globalBizSerno) {
        this.globalBizSerno = globalBizSerno;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }


    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }


    public String getPreNodeId() {
        return preNodeId;
    }

    public void setPreNodeId(String preNodeId) {
        this.preNodeId = preNodeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPreTaskId() {
        return preTaskId;
    }

    public void setPreTaskId(String preTaskId) {
        this.preTaskId = preTaskId;
    }

    public String getBizSerno() {
        return bizSerno;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setBizSerno(String bizSerno) {
        this.bizSerno = bizSerno;
    }

    public Map<String, Object> getExtParams() {
        return extParams;
    }

    public void setExtParams(Map<String, Object> extParams) {
        this.extParams = extParams;
    }


    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getInstOrgId() {
        return instOrgId;
    }

    public void setInstOrgId(String instOrgId) {
        this.instOrgId = instOrgId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getMainProcInstId() {
        return mainProcInstId;
    }

    public void setMainProcInstId(String mainProcInstId) {
        this.mainProcInstId = mainProcInstId;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public NvInsApproval getInstApproval() {
        return instApproval;
    }

    public void setInstApproval(NvInsApproval instApproval) {
        this.instApproval = instApproval;
    }

    public List<Approver> getNextApprovers() {
        return nextApprovers;
    }

    public void setNextApprovers(List<Approver> nextApprovers) {
        this.nextApprovers = nextApprovers;
    }


}
