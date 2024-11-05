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

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDateTime dueDate;

    @NotBlank
    private Long categoryId;

    @Schema(description = "우선순위 >> TOP, MIDDLE, BOTTOM")
    private Priority priority;

    @Schema(description = "상태 >> PENDING, PROGRESS, COMPLETED")
    @NotBlank
    private Status status;

    public static Task dtoToEntity(TaskRequestDto requestDto, Category category, User user) {
        return Task.builder()
                .title(requestDto.title)
                .description(requestDto.description)
                .dueDate(requestDto.dueDate)
                .status(requestDto.status)
                .priority(requestDto.priority)
                .category(category)
                .createdAt(LocalDateTime.now())
                .category(category)
                .user(user)
                .build();
    }
}
