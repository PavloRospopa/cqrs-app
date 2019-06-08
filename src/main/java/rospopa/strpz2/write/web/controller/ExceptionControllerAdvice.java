package rospopa.strpz2.write.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rospopa.strpz2.write.web.dto.ErrorDto;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
class ExceptionControllerAdvice {
    private static final String ILLEGAL_STATE_MSG = "Illegal State";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    ErrorDto handle(IllegalArgumentException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    ErrorDto handle(EntityNotFoundException exception) {
        return new ErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalStateException.class)
    ErrorDto handle(IllegalStateException exception) {
        return new ErrorDto(ILLEGAL_STATE_MSG, exception.getMessage());
    }
}
