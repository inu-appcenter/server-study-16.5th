package com.appcenter.todo_list.repository;

import com.appcenter.todo_list.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
