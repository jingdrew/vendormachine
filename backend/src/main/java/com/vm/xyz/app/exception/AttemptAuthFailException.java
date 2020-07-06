package com.vm.xyz.app.exception;

public class AttemptAuthFailException extends RuntimeException {
    public AttemptAuthFailException() {
        super("Failure to attempt to authenticate.");
    }

    public AttemptAuthFailException(String message) {
        super(message);
    }

}
