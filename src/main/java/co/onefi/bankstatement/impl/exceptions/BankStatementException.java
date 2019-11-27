package co.onefi.bankstatement.impl.exceptions;

/**
 *
 * @author michael
 */
public class BankStatementException extends BaseException {

    public BankStatementException(String message) {
        super(message);
    }

    public BankStatementException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankStatementException(Throwable cause) {
        super(cause);
    }

}
