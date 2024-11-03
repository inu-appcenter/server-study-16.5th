package org.todo.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "투두 삭제 req dto")
@Getter
@NoArgsConstructor
public class TodoUpdateRequestDto {

    @Schema(description = "삭제를 요청한 유저 아이디", example = "1")
    @NotBlank
    private Long userId;

    @NotBlank
    private String title;

    @Size(max = 100)
    private String description;

    @NotBlank
    private LocalDateTime dueDate;

    @NotBlank
    @Min(value = 0, message = "최소 = 0(매우매우 중요)")
    @Max(value = 4, message = "최대 = 4(매우매우 안중요)")
    private Integer priority;
}
