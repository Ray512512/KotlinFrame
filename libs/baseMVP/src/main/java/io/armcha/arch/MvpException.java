package io.armcha.arch;

/**
 */

class MvpException extends RuntimeException {

    MvpException(String message) {
        super(message);
    }

    MvpException(String message, Throwable cause) {
        super(message, cause);
    }

    MvpException(Throwable cause) {
        super(cause);
    }
}
