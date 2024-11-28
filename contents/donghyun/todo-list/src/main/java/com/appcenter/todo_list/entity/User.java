package com.appcenter.todo_list.entity;

import com.appcenter.todo_list.dto.request.UserRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 1000)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Task> tasks;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Category> Categories;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @Builder
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createAt = LocalDateTime.now();
    }

    public User(UserRequestDto request) {
        this.email = request.getEmail();
        this.name = request.getName();
        this.createAt = LocalDateTime.now();
    }

    public User update(UserRequestDto userRequestDto) {
        this.name = userRequestDto.getName();
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
        this.createAt = LocalDateTime.now();

        return this;
    }

    public void updatePassword(String encode) {
        this.password = encode;
    }
}
