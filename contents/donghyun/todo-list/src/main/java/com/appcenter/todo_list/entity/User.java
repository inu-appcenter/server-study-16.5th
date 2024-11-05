package com.appcenter.todo_list.entity;

import com.appcenter.todo_list.dto.request.UserRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Task> tasks;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Category> Categories;

    public User update(UserRequestDto userRequestDto) {
        this.name = userRequestDto.getName();
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
        this.createAt = LocalDateTime.now();

        return this;
    }
}
