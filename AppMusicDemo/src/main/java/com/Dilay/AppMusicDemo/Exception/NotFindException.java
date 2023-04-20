package com.Dilay.AppMusicDemo.Exception;



public class NotFindException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public NotFindException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatusCode() {
        return errorCode.getHttpCode();
    }

    public String getErrorCode() {
        return errorCode.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

