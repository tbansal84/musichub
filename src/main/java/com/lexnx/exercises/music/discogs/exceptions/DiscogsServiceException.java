package com.lexnx.exercises.music.discogs.exceptions;

public class DiscogsServiceException extends RuntimeException {
    public DiscogsServiceException() {
    }

    public DiscogsServiceException(String message) {
        super(message);
    }

    public DiscogsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscogsServiceException(Throwable cause) {
        super(cause);
    }

    public DiscogsServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
