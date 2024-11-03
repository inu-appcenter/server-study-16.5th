package com.appcenter.todo_list.repository;

import com.appcenter.todo_list.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
