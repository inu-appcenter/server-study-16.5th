package com.appcenter.todo_list.repository;

import com.appcenter.todo_list.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
