package com.appcenter.todo_list.entity;

import com.appcenter.todo_list.dto.request.TaskRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public Task update(TaskRequestDto taskRequestDto, Category category) {
        this.category = category;
        this.title = taskRequestDto.getTitle();
        this.description = taskRequestDto.getDescription();
        this.dueDate = taskRequestDto.getDueDate();
        this.priority = taskRequestDto.getPriority();
        this.status = taskRequestDto.getStatus();
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();

        return this;
    }
}
