package org.todo.todo.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.todo.todo.global.error.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
