package jantoni1.librarybackend.controller;

import jantoni1.librarybackend.exception.BookException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({BookException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleBookNotFound() {
    }

}
