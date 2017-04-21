package com.afrunt.jach;

/**
 * @author Andrii Frunt
 */
public class ACHException extends RuntimeException {

    public ACHException() {
    }

    public ACHException(String message) {
        super(message);
    }

    public ACHException(String message, Throwable cause) {
        super(message, cause);
    }

    public ACHException(Throwable cause) {
        super(cause);
    }
}
