package com.appcenter.todo_list.dto.request;

import com.appcenter.todo_list.entity.Priority;
import com.appcenter.todo_list.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private LocalDateTime dueDate;

    @NotBlank
    private Long categoryId;

    @Schema(description = "우선순위 >> TOP, MIDDLE, BOTTOM")
    private Priority priority;

    @Schema(description = "상태 >> PENDING, PROGRESS, COMPLETED")
    @NotBlank
    private Status status;
}
