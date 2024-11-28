package org.todo.todo.global.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{
    // common error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
    // todos error
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Todo not found"),
    // user error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "User not found"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, 403, "Permission denied"),

    // jwt
    JWT_NOT_VALID(HttpStatus.UNAUTHORIZED, 401, "Jwt is not Valid"),
    // validation
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, 400, "Validation Failed")
    ;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
