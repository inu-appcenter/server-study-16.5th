package org.todo.todo.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.todo.todo.global.error.code.CustomErrorCode;
import org.todo.todo.global.error.code.ErrorCode;
import org.todo.todo.global.error.exception.RestApiException;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e){
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e){
        ErrorCode errorCode = CustomErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    public ErrorResponse makeErrorResponse(ErrorCode errorCode){
        return ErrorResponse.builder()
                .error(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
