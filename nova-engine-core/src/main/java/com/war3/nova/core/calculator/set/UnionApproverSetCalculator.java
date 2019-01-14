package com.war3.nova.core.calculator.set;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.core.calculator.approval.ApproverSetCalculator;
import com.war3.nova.core.enumeration.ApproverSetType;
import com.war3.nova.core.util.Novas;

/**
 * 并集
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午3:48:03
 * @version 1.0
 */
@NovaMapper(enumClass = ApproverSetType.class, enumValue = "U", mapperName = Constants.APRV_SET_CALCULATOR)
public class UnionApproverSetCalculator implements ApproverSetCalculator {

    @Override
    public List<Approver> calculate(List<List<Approver>> approvers) throws NovaException {
        List<Approver> list = new LinkedList<Approver>();
        if (Novas.nonNullOrEmpty(approvers)) {
            approvers.forEach(s -> list.addAll(s));
            Map<String, Approver> tempMap = new HashMap<String, Approver>();
            list.forEach(s -> tempMap.put(s.getAprvUser(), s));
            return tempMap.values().stream().collect(Collectors.toList());
        }
        return list;
    }

}
