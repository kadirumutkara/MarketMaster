package com.ing.marketMaster.handler;

import com.ing.marketMaster.exception.ApplicationException;
import com.ing.marketMaster.exception.BusinessException;
import com.ing.marketMaster.exception.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        log.error("Business exception occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Error Code: " + ex.getCode() + ", Message: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleApplicationException(ApplicationException ex) {
        log.error("Application exception occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Error Code: " + ex.getCode() + ", Message: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Error Code: " + ErrorCodes.GENERAL_ERROR + ", Message: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
