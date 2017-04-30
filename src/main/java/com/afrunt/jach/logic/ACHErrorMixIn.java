package com.afrunt.jach.logic;

import com.afrunt.jach.exception.ACHException;

/**
 * @author Andrii Frunt
 */
public interface ACHErrorMixIn {
    default void throwError(String message) throws ACHException {
        throw error(message);
    }

    default ACHException error(String message) {
        return new ACHException(message);
    }

    default ACHException error(String message, Throwable e) {
        return new ACHException(message, e);
    }
}
