package com.war3.nova.core.calculator.approval.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.NovaException;
import com.war3.nova.beans.Approver;
import com.war3.nova.beans.Nova;
import com.war3.nova.beans.NvApprover;
import com.war3.nova.core.calculator.approval.ApproverRangeCalculator;
import com.war3.nova.core.util.Strings;

/**
 * 审批人员计算
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午3:19:29
 * @version 1.0
 */
public abstract class AbstractApproverRangeCalculator implements ApproverRangeCalculator {
    
    private final static Logger logger = LoggerFactory.getLogger(AbstractApproverRangeCalculator.class);
    
    ThreadLocal<NvApprover> approver;
    
    // spring bean 默认的单例模式，故nvApprover对象用线程变量存储
    public void setCalculateApprover(NvApprover nvApprover) {
        this.approver = new ThreadLocal<NvApprover>() {
            protected NvApprover initialValue() {
                return nvApprover;
            }
        };
    }
    
    @Override
    public List<Approver> calculate(Nova nova) throws NovaException {
        ProcessStartUser startUser = new ProcessStartUser(nova.getStartUser(), nova.getOrgId(), nova.getInstOrgId());
        return doCalculate(approver.get(), nova.getProcessId(), nova.getNodeId(), startUser);
        
    }
    
    public List<Approver> doCalculate(NvApprover approver, String processId, String nodeId, ProcessStartUser startUser) {
        logger.info("流程[{}], 节点[{}], 人员审批信息范围计算开始, 配置为:{}", processId, nodeId, approver.getValue());
        RangeService rangeService = this.getRangeService();
        if (Objects.nonNull(rangeService)) {
            List<User> users = rangeService.queryUserByIds(Strings.split(approver.getValue(), ";"));
            return users.parallelStream().map(s -> userToApprover(s)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    public abstract RangeService getRangeService();
    
    /**
     * user to Approver
     * @param user
     * @return
     */
    protected Approver userToApprover(User user) {
        Approver approver = new Approver();
        approver.setAprvInstOrg(user.getInstOrgId());
        approver.setAprvOrg(user.getOrgId());
        approver.setAprvOrgName(user.getOrgName());
        approver.setAprvUser(user.getUserId());
        approver.setAprvUserName(user.getUserName());
        return approver;
    }

}
