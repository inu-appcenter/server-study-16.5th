package com.appcenter.todo_list.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // User Errors
    USER_NOT_FOUND(404, "U001", "해당 User를 찾을 수 없습니다."),
    DUPLICATE_USER(409, "U002", "해당 User가 이미 존재합니다."),
    NOT_EXITS_PASSWORD(404, "U003", "비밀번호가 존재하지 않습니다."),


    // Task Errors
    TASK_NOT_FOUND(404, "T001", "해당 Task를 찾을 수 없습니다."),
    DUPLICATE_TASK(409, "T002", "해당 Task가 이미 존재합니다."),

    // Category Errors
    CATEGORY_NOT_FOUND(404, "CAT001", "해당 Category를 찾을 수 없습니다."),
    DUPLICATE_CATEGORY(409, "CAT002", "해당 Category가 이미 존재합니다."),

    INPUT_VALUE_INVALID(400, "IVI001", "입력이 잘못되었습니다.");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}