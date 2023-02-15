package com.gustavomoura.softdesign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StaveSessionExpiredException extends RuntimeException{

    public StaveSessionExpiredException(String exception) {
        super(exception);
    }
}
