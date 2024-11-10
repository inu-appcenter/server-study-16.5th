package com.appcenter.todo_list.repository;

import com.appcenter.todo_list.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategoryId(Long categoryId);
    List<Task> findByUserId(Long userId);
    List<Task> findByCategoryIdAndUserId(Long categoryId, Long userId);
}
