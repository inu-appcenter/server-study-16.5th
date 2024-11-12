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
    // TODO DateTimeFormat 에러 핸들링 추가(Custom Validator)
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDateTime requestDate;
}
