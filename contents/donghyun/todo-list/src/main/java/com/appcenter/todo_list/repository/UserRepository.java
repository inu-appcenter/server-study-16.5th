package com.appcenter.todo_list.repository;

import com.appcenter.todo_list.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
