package com.lexnx.exercises.music.service.exceptions;

public class ArtistServiceException extends RuntimeException {
    public ArtistServiceException() {
    }

    public ArtistServiceException(String message) {
        super(message);
    }

    public ArtistServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtistServiceException(Throwable cause) {
        super(cause);
    }

    public ArtistServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
