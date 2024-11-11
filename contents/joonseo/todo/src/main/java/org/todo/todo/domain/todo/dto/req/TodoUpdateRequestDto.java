package org.todo.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "투두 수정 req dto")
@Getter
@NoArgsConstructor
public class TodoUpdateRequestDto {
    @NotBlank(message = "투두 ID가 입력되지 않았습니다.")
    private Long todoId;

    @NotBlank(message = "제목이 입력되지 않았습니다.")
    private String title;

    @Size(max = 100, message = "내용의 최대 길이는 100입니다.")
    private String description;

    @NotBlank(message = "마감일이 지정되지 않았습니다.")
    private LocalDateTime dueDate;

    @NotBlank(message = "우선순위가 입력되지 않았습니다.")
    @Min(value = 0, message = "우선순위의 최소값은 0입니다.")
    @Max(value = 4, message = "우선순위의 최대값은 4입니다.")
    private Integer priority;
}
