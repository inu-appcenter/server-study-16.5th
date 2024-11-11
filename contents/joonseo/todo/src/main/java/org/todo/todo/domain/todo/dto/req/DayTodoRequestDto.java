package org.todo.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "일별 투두 req dto")
@Getter
@NoArgsConstructor
public class DayTodoRequestDto {

    @Schema(description = "투두 조회를 요청한 유저 아이디", example = "1")
    @NotBlank
    private Long userId;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDateTime createdAt;
}
