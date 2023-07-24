package com.snsproj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated user name"),
    ;

    private final HttpStatus status;
    private final String message;
}