package com.war3.nova.core.customized;

import java.util.Objects;

import com.war3.nova.core.AbstractResult;

/**
 * boolean型返回结果
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午9:02:36
 * @version 1.0
 */
public class BooleanResult extends AbstractResult<Boolean> {
    
    private Boolean result;
    
    public BooleanResult(boolean result, boolean isSuccess, String errorCode, String errorMessage) {
        super(isSuccess, errorCode, errorMessage);
        this.result = result;
    }
    
    public final static BooleanResult createSuccess(boolean result) {
        return new BooleanResult(result, true, null, null);
    }

    public final static BooleanResult createFailure(String errorCode, String errorMessage) {
        return new BooleanResult(false, false, errorCode, errorMessage);
    }
    
    public Boolean getResult() {
        return Objects.isNull(result) ? false : result;
    }

}
