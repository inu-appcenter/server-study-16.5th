package org.todo.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.todo.domain.todo.entity.TodoEntity;
import org.todo.todo.domain.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Optional<TodoEntity> findByTitle(String title);
    List<TodoEntity> findAllByUserId(Long userId);
    List<TodoEntity> findAllByCreatedAtAndUserId(LocalDateTime createdAt, Long userId);
}
