package com.war3.nova.core.customized;

import com.war3.nova.core.AbstractResult;

/**
 * 返回对象
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午9:07:29
 * @version 1.0
 */
public class ObjectResult extends AbstractResult<Object> {
    
    private Object result;
    
    public ObjectResult(Object result, boolean isSuccess, String errorCode, String errorMessage) {
        super(isSuccess, errorCode, errorMessage);
        this.result = result;
    }
    
    public final static ObjectResult createSuccess(Object result) {
        return new ObjectResult(result, true, null, null);
    }
    
    public final static ObjectResult createFailure(String errorCode, String errorMessage) {
        return new ObjectResult(null, false, errorCode, errorMessage);
    }

    public Object getResult() {
        return result;
    }
}
