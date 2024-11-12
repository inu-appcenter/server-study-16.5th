package org.todo.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "투두 등록 req dto")
@Getter
@NoArgsConstructor
public class TodoDto {

    @Schema(description = "제목", example = "지형이랑 카페가서 공부하기")
    @NotBlank(message = "제목이 입력되지 않았습니다.")
    private String title;

    @Schema(description = "내용", example = "카페 꼼마에서 오전 11시 만남")
    @Size(max = 100, message = "내용의 최대 길이는 100입니다.")
    private String description;

    @Schema(description = "마감일", example = "YYYY-MM-DD")
    @NotBlank(message = "마감일이 지정되지 않았습니다.")
    private LocalDateTime dueDate;

    @Schema(description = "우선순위", example = "1")
    @NotBlank(message = "우선순위가 입력되지 않았습니다.")
    @Min(value = 0, message = "우선순위의 최소값은 0입니다.")
    @Max(value = 4, message = "우선순위의 최대값은 4입니다.")
    private Integer priority;
}
