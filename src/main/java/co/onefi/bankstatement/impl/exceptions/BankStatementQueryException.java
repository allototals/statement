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
public class BankStatementQueryException extends BaseException {

    public BankStatementQueryException(String message) {
        super(message);
    }

    public BankStatementQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankStatementQueryException(Throwable cause) {
        super(cause);
    }

}
