package org.todo.todo.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.todo.todo.global.error.code.CustomErrorCode;
import org.todo.todo.global.error.code.ErrorCode;
import org.todo.todo.global.error.exception.RestApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse<String>> handleRestApiException(RestApiException e){
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<String>>> handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        log.error("@Valid Exception occur with below parameter");
        for (FieldError error : result.getFieldErrors()){
            String errorMessage = "[ " + error.getField() + " ]" +
                    "[ " + error.getDefaultMessage() + " ]" +
                    "[ " + error.getRejectedValue() + " ]";
            errorMessages.add(errorMessage);
        }

        return handleExceptionInternal(CustomErrorCode.INVALID_PARAMS, errorMessages);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleException(Exception e){
        ErrorCode errorCode = CustomErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<ErrorResponse<String>> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ResponseEntity<ErrorResponse<List<String>>> handleExceptionInternal(ErrorCode errorCode, List<String> message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse<String> makeErrorResponse(ErrorCode errorCode){
        return ErrorResponse.<String>builder()
                .error(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    private ErrorResponse<List<String>> makeErrorResponse(ErrorCode errorCode, List<String> message){
        return ErrorResponse.<List<String>>builder()
                .error(errorCode.getCode())
                .message(message)
                .build();
    }
}