package com.gustavomoura.softdesign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StaveVotedException extends RuntimeException{

    public StaveVotedException(String exception) {
        super(exception);
    }
}
