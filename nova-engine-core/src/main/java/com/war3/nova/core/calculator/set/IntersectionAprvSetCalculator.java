package com.war3.nova.core.calculator.set;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.annotation.NovaMapper;
import com.war3.nova.beans.Approver;
import com.war3.nova.core.calculator.approval.ApproverSetCalculator;
import com.war3.nova.core.enumeration.ApproverSetType;
import com.war3.nova.core.util.Novas;

/**
 * 交集
 * 
 * @author Cytus_
 * @since 2018年12月17日 下午3:47:47
 * @version 1.0
 */
@NovaMapper(enumClass = ApproverSetType.class, enumValue = "I", mapperName = Constants.APRV_SET_CALCULATOR)
public class IntersectionAprvSetCalculator implements ApproverSetCalculator {

    @Override
    public List<Approver> calculate(List<List<Approver>> approvers) throws NovaException {
        List<Approver> list = new LinkedList<Approver>();
        if (Novas.nonNullOrEmpty(approvers)) {
            approvers.forEach(s -> list.addAll(s));
            Map<String, Integer> numMap = new ConcurrentHashMap<String, Integer>();
            Map<String, Approver> dataMap = new HashMap<String, Approver>();
            list.forEach(s -> {
                numMap.put(s.getAprvUser(), numMap.getOrDefault(s.getAprvUser(), 0) + 1);
                dataMap.put(s.getAprvUser(), s);
            });
            return dataMap.values().parallelStream().filter(s -> numMap.get(s.getAprvUser()) > 1).collect(Collectors.toList());
        }
        return list;
    }

}
