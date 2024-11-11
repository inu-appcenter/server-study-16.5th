package com.appcenter.todo_list.dto.request;

import com.appcenter.todo_list.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "User 요청 DTO")
public class UserRequestDto {

    @NotBlank(message = "이메일은 반드시 입력하셔야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$",
            message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "이름은 반드시 입력하셔야 합니다.")
    private String name;

    @NotBlank(message = "패스워드는 반드시 입력하셔야 합니다.")
    @Size(min = 8, message = "패스워드는 최소 8자리 이상이여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$", message = "패스워드는 최소 하나의 영어와 숫자가 필요합니다.")
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
