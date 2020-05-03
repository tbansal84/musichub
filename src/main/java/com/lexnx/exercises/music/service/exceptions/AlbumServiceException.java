package com.lexnx.exercises.music.service.exceptions;

public class AlbumServiceException extends RuntimeException {
    public AlbumServiceException() {
    }

    public AlbumServiceException(String message) {
        super(message);
    }

    public AlbumServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlbumServiceException(Throwable cause) {
        super(cause);
    }

    public AlbumServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
