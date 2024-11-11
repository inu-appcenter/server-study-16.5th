package org.todo.todo.global.error;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final Integer error;
    private final String message;
}
