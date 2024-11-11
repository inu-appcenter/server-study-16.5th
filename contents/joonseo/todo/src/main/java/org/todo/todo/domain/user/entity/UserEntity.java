package org.todo.todo.domain.user.entity;

import lombok.*;

import jakarta.persistence.*;
import org.todo.todo.domain.todo.entity.TodoEntity;
import org.todo.todo.global.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Builder
    public UserEntity(String email, String password, String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoEntity> todos;
}
