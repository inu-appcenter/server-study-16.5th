package org.todo.todo.domain.todo.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private final T data;
    private final String message;

    @Builder
    private ResponseDto(T data, String message){
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseDto<T> of(T data, String message){
        return ResponseDto.<T>builder()
                .data(data)
                .message(message)
                .build();
    }
}