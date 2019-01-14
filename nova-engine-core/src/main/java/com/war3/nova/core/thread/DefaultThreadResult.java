package com.war3.nova.core.thread;

import com.war3.nova.core.AbstractResult;

/**
 * 
 * 
 * 
 * @author Cytus_
 * @since 2018年5月28日 上午11:15:36
 * @version 1.0
 *
 */
public class DefaultThreadResult extends AbstractResult<Object> {
	
	private Object result;
	
	public DefaultThreadResult(Object result, boolean isSuccess, String errorCode, String errorMessage) {
		super(isSuccess, errorCode, errorMessage);
		this.result = result;
	}
	
	public final static DefaultThreadResult createSuccess(Object result) {
		return new DefaultThreadResult(result, true, null, null);
	}
	

	public Object getResult() {
		return result;
	}

}
