package com.vm.xyz.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class XyzException {

    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
}
