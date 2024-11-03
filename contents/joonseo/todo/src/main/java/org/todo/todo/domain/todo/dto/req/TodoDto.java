package org.todo.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "투두 등록/수정 req dto")
@Getter
@NoArgsConstructor
public class TodoDto {

    @Schema(description = "등록을 요청한 유저 아이디", example = "1")
    @NotBlank
    private Long userId;

    @Schema(description = "제목", example = "지형이랑 카페가서 공부하기")
    @NotBlank
    private String title;

    @Schema(description = "내용", example = "카페 꼼마에서 오전 11시 만남")
    @Size(max = 100)
    private String description;

    @Schema(description = "마감일", example = "YYYY-MM-DD")
    @NotBlank
    private LocalDateTime dueDate;

    @Schema(description = "우선순위", example = "1")
    @NotBlank
    @Min(value = 0, message = "최소 = 0(매우매우 중요)")
    @Max(value = 4, message = "최대 = 4(매우매우 안중요)")
    private Integer priority;

}
