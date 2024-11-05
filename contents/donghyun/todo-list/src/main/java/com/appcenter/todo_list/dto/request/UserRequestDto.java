package com.appcenter.todo_list.dto.request;

import com.appcenter.todo_list.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "User 요청 DTO")
public class UserRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public static User dtoToEntity(UserRequestDto requestDto){
        return User.builder()
                .email(requestDto.email)
                .password(requestDto.password)
                .name(requestDto.name)
                .createAt(LocalDateTime.now())
                .build();
    }
}
