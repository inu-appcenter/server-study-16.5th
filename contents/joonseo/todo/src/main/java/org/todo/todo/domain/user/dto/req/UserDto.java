package org.todo.todo.domain.user.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 등록 req dto")
@Getter
@NoArgsConstructor
public class UserDto {
    @Schema(description = "이메일", example = "ahh0520@naver.com")
    @NotBlank @Email
    private String email;

    @Schema(description = "비밀번호", example = "apfhd412@")
    @NotBlank
    private String password;

    @Schema(description = "이름", example = "한준서")
    @NotBlank
    private String username;
}