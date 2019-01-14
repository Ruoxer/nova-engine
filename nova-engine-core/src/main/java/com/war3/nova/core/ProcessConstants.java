package com.war3.nova.core;

/**
 * 
 * 静态常量类
 * 
 * @author Cytus_
 * @since 2018年5月29日 下午7:16:07
 * @version 1.0
 *
 */
public final class ProcessConstants {
    
    // 状态-运行中
    public static String STATUS_RUNNING = "01";
    // 状态-审批中
    public static String STATUS_APPROVAL = "03";
    // 状态-拒绝
    public static String STATUS_REFUSE = "02";
    // 状态-异常
    public static String STATUS_EXCEPTION = "04";
    // 状态-结束
    public static String STATUS_END = "99";
    
    // 审批状态-通过
    public static String ARRV_RESULT_PASS = "01";
    
    // 审批状态-拒绝
    public static String ARRV_RESULT_REFUSE = STATUS_REFUSE;
    
    // 系统处理用户编号
    public static String SYSTEM_USER = "SystemUser";
    
    // 系统处理用户名称
    public static String SYSTEM_USER_NAME = "系统默认用户";
    
    // 系统处理机构编号
    public static String SYSTEM_ORG = "SystemOrg";
    
    // 系统处理机构名称
    public static String SYSTEM_ORG_NAME = "系统默认机构";
    
    // 系统处理金融机构编号
    public static String SYSTEM_INST_ORG = "SysInstOrg";
    
    // 默认的任务池用户编号
    public static String POOL_USER = "PoolUser";
    
    // 默认的任务池用户名
    public static String POOL_USER_NAME = "任务池";
}