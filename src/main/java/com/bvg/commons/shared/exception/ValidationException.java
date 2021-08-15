package com.bvg.commons.shared.exception;

@SuppressWarnings("serial")
public class ValidationException extends AnyServiceException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message, null);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Exception e) {
        super(e.getMessage(), e);
    }

}
