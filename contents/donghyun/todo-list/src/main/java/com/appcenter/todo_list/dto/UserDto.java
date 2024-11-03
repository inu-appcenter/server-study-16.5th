package com.appcenter.todo_list.dto;

import com.appcenter.todo_list.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "User DTO")
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime createdAt;

    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId()).email(user.getEmail())
                .password(user.getPassword()).name(user.getName()).createdAt(user.getCreateAt())
                .build();
    }
}
