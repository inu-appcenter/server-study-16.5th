package com.appcenter.todo_list.dto.response;

import com.appcenter.todo_list.dto.CategoryDto;
import com.appcenter.todo_list.dto.UserDto;
import com.appcenter.todo_list.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@Schema(description = "Task 응답 DTO")
public class TaskResponseDto {

    private Long taskId;
    private UserDto userDto;
    private CategoryDto categoryDto;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "우선순위 >> TOP, MIDDLE, BOTTOM")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Schema(description = "상태 >> PENDING, PROGRESS, COMPLETED")
    private Priority priority;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TaskResponseDto entityToDto(Task task) {
        return TaskResponseDto.builder()
                .taskId(task.getId())
                .userDto(UserDto.entityToDto(task.getUser()))
                .categoryDto(CategoryDto.entityToDto(task.getCategory()))
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

}
