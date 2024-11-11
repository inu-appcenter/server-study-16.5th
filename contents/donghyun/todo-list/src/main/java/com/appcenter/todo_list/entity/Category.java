package com.appcenter.todo_list.entity;

import com.appcenter.todo_list.dto.request.CategoryRequestDto;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(length = 50)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Category(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }

    public Category update(CategoryRequestDto categoryRequestDto) {
        this.name = categoryRequestDto.getName();
        this.description = categoryRequestDto.getDescription();

        return this;
    }
}
