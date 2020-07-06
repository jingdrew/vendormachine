package com.vm.xyz.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class XyzExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException e) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        XyzException exception = new XyzException(e.getMessage(), status, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        XyzException exception = new XyzException(e.getMessage(), status, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = AttemptAuthFailException.class)
    public ResponseEntity<Object> handleAttemptAuthFailException(AttemptAuthFailException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        XyzException exception = new XyzException(e.getMessage(), status, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = ParseException.class)
    public ResponseEntity<Object> handleParseException(ParseException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        XyzException exception = new XyzException(e.getMessage(), status, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, status);
    }
}
