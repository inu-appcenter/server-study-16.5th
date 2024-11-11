package com.appcenter.todo_list.entity;

import com.appcenter.todo_list.dto.request.TaskRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(length = 100)
    private String description;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Task(String title, String description, LocalDateTime dueDate, Status status, Priority priority, LocalDateTime createdAt, LocalDateTime updatedAt, User user, Category category) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.category = category;
    }

    public Task update(TaskRequestDto taskRequestDto, Category category) {
        this.category = category;
        this.title = taskRequestDto.getTitle();
        this.description = taskRequestDto.getDescription();
        this.dueDate = taskRequestDto.getDueDate();
        this.priority = Priority.parsing(taskRequestDto.getPriority());
        this.status = Status.parsing(taskRequestDto.getStatus());
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();

        return this;
    }
}
