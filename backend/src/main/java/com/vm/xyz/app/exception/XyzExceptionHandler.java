package com.vm.xyz.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;

@ControllerAdvice
public class XyzExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException e) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        XyzException exception = new XyzException(e.getMessage(), status);
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        XyzException exception = new XyzException(e.getMessage(), status);
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        XyzException exception = new XyzException(e.getMessage(), status);
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        XyzException exception = new XyzException(e.getMessage(), status);
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = ParseException.class)
    public ResponseEntity<Object> handleParseException(ParseException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        XyzException exception = new XyzException(e.getMessage(), status);
        return new ResponseEntity<>(exception, status);
    }
}
