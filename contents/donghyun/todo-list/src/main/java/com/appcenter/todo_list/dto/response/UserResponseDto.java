package com.appcenter.todo_list.dto.response;

import com.appcenter.todo_list.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserResponseDto {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private LocalDateTime createdAt;

    public static UserResponseDto entityToDto(User user) {
        return UserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .createdAt(user.getCreateAt())
                .build();
    }
}
