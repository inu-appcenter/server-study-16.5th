package org.todo.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "투두 완료/미완료 업데이트 req dto")
@Getter
@NoArgsConstructor
public class TodoCompletedRequestDto {
    @Schema(description = "완료 상태 업데이트를 요청한 유저 아이디", example = "1")
    @NotBlank
    private Long userId;

    @Schema(description = "제목", example = "지형이랑 카페가서 공부하기")
    @NotBlank
    private String title;
}
