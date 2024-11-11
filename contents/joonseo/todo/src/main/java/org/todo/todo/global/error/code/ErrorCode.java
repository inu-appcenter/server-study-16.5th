package org.todo.todo.global.error.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
    Integer getCode();
    String getMessage();
}
