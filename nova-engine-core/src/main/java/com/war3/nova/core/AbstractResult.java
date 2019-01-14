package com.war3.nova.core;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.war3.nova.Result;

/**
 * 
 * @param <T>
 * 
 * @author Cytus_
 * @since 2018年5月28日 上午11:15:43
 * @version 1.0
 *
 */
public abstract class AbstractResult<T> implements Result<T> {

	protected boolean isSuccess;
	
	protected String errorCode;
	
	protected String errorMessage;
	
	public AbstractResult() {
		super();
	}

	public AbstractResult(boolean isSuccess, String errorCode, String errorMessage) {
		super();
		this.isSuccess = isSuccess;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String errorCode() {
		return this.errorCode;
	}

	public String errorMessage() {
		return this.errorMessage;
	}
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
