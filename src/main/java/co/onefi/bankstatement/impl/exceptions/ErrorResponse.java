/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl.exceptions;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.Map;
import java.util.Optional;
import lombok.Data;

/**
 *
 * @author michael
 */
@Data
public class ErrorResponse {

    private String code;
    private String message;
    private Map<String, Object> errorData;

    public ErrorResponse(BaseException exception) {
        code = Optional.ofNullable(exception.getErrorCode())
                .orElse(exception.getClass().getSimpleName());
        message = exception.getMessage();
        errorData = exception.getErrorData();
    }

    public ErrorResponse(Exception exception) {
        code = exception.getClass().getSimpleName();
        message = exception.getMessage();
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String code, String message) {
        this.message = message;
        this.code = code;
    }

    @JsonAnyGetter
    public Map<String, Object> getErrorData() {
        return errorData;
    }

}
