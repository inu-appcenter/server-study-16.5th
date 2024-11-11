package com.appcenter.todo_list.dto.response;

import com.appcenter.todo_list.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
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

    @Schema(description = "우선순위 >> TOP, MIDDLE, BOTTOM")
    private Status status;

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

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @Schema(description = "Category DTO")
    public static class CategoryDto {

        private Long id;
        private String name;
        private String description;

        public static CategoryDto entityToDto(Category category) {
            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();
        }
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @Schema(description = "User DTO")
    public static class UserDto {

        private Long id;
        private String email;
        private String password;
        private String name;
        private LocalDateTime createdAt;

        public static UserDto entityToDto(User user) {
            return UserDto.builder()
                    .id(user.getId()).email(user.getEmail())
                    .password(user.getPassword()).name(user.getName()).createdAt(user.getCreateAt())
                    .build();
        }
    }
}
