package com.ing.marketMaster.exception;

public class BusinessException extends ApplicationException {
    public BusinessException(String message, int code) {
        super(message, code);
    }
}