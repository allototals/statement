/*
 * Copyright (C) One Finance & Investments Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by OneFi Developers <developers@onefi.co>, 2019
 */
package co.onefi.bankstatement.impl.exceptions;

/**
 *
 * @author taiwo
 */
public class AccountValidationException extends BaseException {

    public AccountValidationException() {
    }

    public AccountValidationException(String message) {
        super(message);
    }

    public AccountValidationException(Throwable cause) {
        super(cause);
    }

    @Override
    public boolean isClientError() {
        return true;
    }

}
