package com.war3.nova.core.calculator.approval.range;

import java.util.List;


/**
 * 范围层级接口
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午4:59:13
 * @version 1.0
 */
public interface RelationshipHierarchyService {
    
    /**
     * 当前
     * @param id
     * @return
     */
    List<User> getCurrentOrg(ProcessStartUser user);
    
    /**
     * 上级
     * @param id
     * @return
     */
    List<User> getSuperOrg(ProcessStartUser user);
    
    /**
     * 上级的下级
     * @param id
     * @return
     */
    List<User> getSuperSubOrg(ProcessStartUser user);
    
    /**
     * 本级及上级
     * @param id
     * @return
     */
    List<User> getCurrAndSuperOrg(ProcessStartUser user);
    
    /**
     * 上上级
     * @param id
     * @return
     */
    List<User> getDoubleSuperOrg(ProcessStartUser user);
    
    /**
     * 上级和上上级
     * @param id
     * @return
     */
    List<User> getSuperAndDoubleSuperOrg(ProcessStartUser user);
    
    /**
     * 上上级的下级
     * @param id
     * @return
     */
    List<User> getDoubleSuperSubOrg(ProcessStartUser user);
    
    /**
     * 下级机构办理者
     * @param id
     * @return
     */
    List<User> getSubOrg(ProcessStartUser user);
    
    /**
     * 流程发起者
     * @param id
     * @return
     */
    List<User> getProcessStartUser(ProcessStartUser user);
    
    /**
     * 已审批
     * @param id
     * @return
     */
    List<User> getApprovedUser(String processInstId);

    /**
     * 机构领导
     * @param id
     * @return
     */
    List<User> getOrgLeader(ProcessStartUser user);
    
    /**
     * 当前部门
     * @param id
     * @return
     */
    List<User> getCurrDept(ProcessStartUser user);
    
}
