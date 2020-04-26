package com.lexnx.exercises.music.configuration;

import com.lexnx.exercises.music.service.exceptions.AlbumNotFoundException;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MusicApplicationExceptonHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(
            ArtistNotFoundException.class)
    protected ResponseEntity<Object> artistNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Artist not found";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(
            AlbumNotFoundException.class)
    protected ResponseEntity<Object> albumNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Album not found";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
