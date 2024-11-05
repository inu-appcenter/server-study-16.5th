package com.appcenter.todo_list.entity;

import com.appcenter.todo_list.dto.request.CategoryRequestDto;
import com.appcenter.todo_list.dto.response.CategoryResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
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

    public Category(CategoryRequestDto categoryRequestDto, User user) {
        this.name = categoryRequestDto.getName();
        this.description = categoryRequestDto.getDescription();
        this.user = user;
    }

    public Category update(CategoryRequestDto categoryRequestDto) {
        this.name = categoryRequestDto.getName();
        this.description = categoryRequestDto.getDescription();

        return this;
    }


}
