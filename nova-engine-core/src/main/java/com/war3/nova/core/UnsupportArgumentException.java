package com.war3.nova.core;

import com.war3.nova.NovaException;

public class UnsupportArgumentException extends NovaException{

    /**
     * 
     */
    private static final long serialVersionUID = -4129242945687224008L;
    
    public UnsupportArgumentException(String message) {
        super(message);
    }

}
