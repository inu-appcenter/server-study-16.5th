package com.appcenter.todo_list.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}