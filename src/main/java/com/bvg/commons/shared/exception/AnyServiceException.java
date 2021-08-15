package com.bvg.commons.shared.exception;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AnyServiceException extends RuntimeException implements Serializable {

    public AnyServiceException() {
        super();
    }

    public AnyServiceException(String message) {
        super(message, null);
    }

    public AnyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnyServiceException(Exception e) {
        super(e.getMessage(), e);
    }

}
