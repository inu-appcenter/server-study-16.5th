package org.todo.todo.domain.todo.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.todo.todo.domain.todo.entity.TodoEntity;

import java.time.LocalDateTime;

@Schema(description = "투두 res dto")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {
    @Schema(description = "제목", example = "지형이랑 카페가서 공부하기")
    private String title;

    @Schema(description = "내용", example = "카페 꼼마에서 오전 11시 만남")
    private String description;

    @Schema(description = "마감일", example = "YYYY-MM-DD")
    private LocalDateTime dueDate;

    @Schema(description = "우선순위", example = "1")
    private Integer priority;

    @Schema(description = "완료여부", example = "true/false")
    private Boolean completed;

    public static TodoResponseDto from(TodoEntity todo){
        return TodoResponseDto.builder()
                .title(todo.getTitle())
                .description(todo.getDescription())
                .dueDate(todo.getDueDate())
                .priority(todo.getPriority())
                .completed(todo.getCompleted())
                .build();
    }
}
