package com.atguigu.crowd.exception;

public class LoginacctAlreadyInUseException extends RuntimeException {
    public LoginacctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginacctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginacctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginacctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
