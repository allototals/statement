/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author michael
 */
@ControllerAdvice
@Slf4j
public class RestErrorHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(BaseException satementEx) {
        ErrorResponse response = new ErrorResponse(satementEx);
        HttpStatus status = satementEx.isClientError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGenericException(Exception satementEx) {
        log.error("UNHANDLED_GENERIC_EXCEPTION occured:", satementEx);
        ErrorResponse response = new ErrorResponse(satementEx);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
