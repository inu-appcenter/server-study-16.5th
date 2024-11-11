package org.todo.todo.global.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error")
    ;


    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
