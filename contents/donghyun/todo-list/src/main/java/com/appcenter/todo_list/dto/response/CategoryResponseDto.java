package com.appcenter.todo_list.dto.response;

import com.appcenter.todo_list.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "Category 응답 DTO")
public class CategoryResponseDto {

    private Long categoryId;
    private String name;
    private String description;

    public static CategoryResponseDto entityToDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
