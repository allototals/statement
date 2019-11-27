/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.onefi.bankstatement.impl.exceptions;

/**
 *
 * @author michael
 */
public class BankStatementParseException extends BaseException {

    public BankStatementParseException(String message) {
        super(message);
    }

    public BankStatementParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankStatementParseException(Throwable cause) {
        super(cause);
    }

}
