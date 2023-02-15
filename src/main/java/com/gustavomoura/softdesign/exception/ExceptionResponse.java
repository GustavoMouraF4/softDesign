package com.gustavomoura.softdesign.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse implements Serializable {

    private LocalDateTime timestamp;
    private String message;
    private String details;

}
