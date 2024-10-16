package org.todo.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.todo.domain.todo.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
