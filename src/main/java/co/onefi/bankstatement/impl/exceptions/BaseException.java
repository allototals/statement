/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author michael
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "errorData")
public class BaseException extends RuntimeException {

    protected boolean clientError;
    protected String errorCode;
    protected final Map<String, Object> errorData = new HashMap<>();

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, boolean clientError) {
        super(message);
        this.clientError = clientError;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public final void addData(String key, Object value) {
        this.errorData.put(key, value);
    }
}
