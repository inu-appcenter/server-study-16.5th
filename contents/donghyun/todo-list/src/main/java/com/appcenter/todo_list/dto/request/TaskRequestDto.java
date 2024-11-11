package com.appcenter.todo_list.dto.request;

import com.appcenter.todo_list.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "Task 요청 DTO")
public class TaskRequestDto {

    @NotBlank(message = "제목을 작성해주세요")
    private String title;

    private String description;

    @NotNull(message = "목표기간을 설정하세요")
    private LocalDateTime dueDate;

    @NotNull(message = "카테고리를 선택하세요")
    private Long categoryId;

    @Schema(description = "우선순위 >> TOP, MIDDLE, BOTTOM")
    private String priority;

    @Schema(description = "상태 >> PENDING, PROGRESS, COMPLETED")
    @NotBlank(message = "현재 상태를 선택하세요")
    private String status;

    public static Task dtoToEntity(TaskRequestDto requestDto, Category category, User user) {
        return Task.builder()
                .title(requestDto.title)
                .description(requestDto.description)
                .dueDate(requestDto.dueDate)
                .status(Status.parsing(requestDto.status))
                .priority(Priority.parsing(requestDto.priority))
                .category(category)
                .createdAt(LocalDateTime.now())
                .category(category)
                .user(user)
                .build();
    }
}
