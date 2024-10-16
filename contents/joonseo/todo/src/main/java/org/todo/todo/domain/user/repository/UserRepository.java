package org.todo.todo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.todo.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
