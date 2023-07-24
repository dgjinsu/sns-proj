package com.snsproj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleSnsApplicationException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public SimpleSnsApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        if(message == null) {
            return errorCode.getMessage();
        }
        return String.format("$s. $s", errorCode.getMessage(), message);
    }
}
