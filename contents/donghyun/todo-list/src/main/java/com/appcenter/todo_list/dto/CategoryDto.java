package com.appcenter.todo_list.dto;

import com.appcenter.todo_list.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "Category DTO")
public class CategoryDto {

    private Long id;
    private String name;
    private String description;

    public static CategoryDto entityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
