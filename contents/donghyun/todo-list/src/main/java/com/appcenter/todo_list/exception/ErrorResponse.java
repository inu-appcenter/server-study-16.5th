package com.appcenter.todo_list.exception;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;


@Getter
@Builder
public class ErrorResponse {
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> of(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

}