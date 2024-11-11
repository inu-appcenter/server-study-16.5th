package com.appcenter.todo_list.dto.request;

import com.appcenter.todo_list.entity.Category;
import com.appcenter.todo_list.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "Category 요청 DTO")
public class CategoryRequestDto {

    @NotBlank(message = "카테고리명을 작성해주세요.")
    private String name;
    private String description;

    public static Category dtoToEntity(CategoryRequestDto requestDto, User user) {
        return Category.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .user(user)
                .build();
    }
}
