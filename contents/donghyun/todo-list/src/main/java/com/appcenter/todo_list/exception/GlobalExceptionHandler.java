package com.appcenter.todo_list.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("커스텀 예외 발생, throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.of(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("Valid 예외 발생, throw MethodArgumentNotValidException : {}", e.getBindingResult().getAllErrors());

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErrorResponse> errorResponses = new ArrayList<>();


        for (FieldError fieldError : fieldErrors) {
            String message = "[ Field : " + fieldError.getField() + "] "
                    + "[ DefaultMessage() : " + fieldError.getDefaultMessage() + "] "
                    + "[ RejectedValue() : " + fieldError.getRejectedValue() + "] ";
            ErrorResponse errorResponse = ErrorResponse.builder()
                            .code(fieldError.getCode())
                                    .message(message)
                                            .build();
            errorResponses.add(errorResponse);
        }
        return ResponseEntity.status(ErrorCode.INPUT_VALUE_INVALID.getStatus()).body(errorResponses);
    }

}