package com.atguigu.crowd.exception;

public class AccessForbiddenException extends Exception {
    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }
}
