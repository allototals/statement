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
public class BankStatementUploadException extends BaseException {

    public BankStatementUploadException(String message) {
        super(message);
    }

    public BankStatementUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankStatementUploadException(Throwable cause) {
        super(cause);
    }

}
