package com.war3.nova.core.actuator.node.artificialapproval;

/**
 * 系统默认会签规则
 * 
 * @author Cytus_
 * @since 2018年12月28日 下午6:07:10
 * @version 1.0
 */
public enum SystemMultiRuleType {
    
    /**
     * 一票否决
     */
    M01("oneVoteVetoMultiJoinSignRule"),
    
    /**
     * 半数否决
     */
    M02("halfVetoMultiJoinSignRule"),
    
    /**
     * 三分之二通过
     */
    M03("two_ThirdsPassedMultiJoinSignRule");
    
    private String value;
    
    private SystemMultiRuleType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
