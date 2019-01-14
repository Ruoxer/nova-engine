package com.war3.nova.core.calculator.approval.range;

import java.util.List;

/**
 * 范围查询用户服务
 * 
 * @author Cytus_
 * @since 2018年12月27日 下午5:22:37
 * @version 1.0
 */
interface RangeService {

    /**
     * 通过ID查询
     * @param ids
     * @return
     */
    List<User> queryUserByIds(String... ids);
    
}
