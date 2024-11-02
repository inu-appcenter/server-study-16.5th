package org.todo.todo.domain.user.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.todo.todo.domain.todo.entity.TodoEntity;
import org.todo.todo.global.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoEntity> todos;
}
