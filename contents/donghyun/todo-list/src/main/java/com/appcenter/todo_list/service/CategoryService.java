package com.appcenter.todo_list.service;

import com.appcenter.todo_list.dto.request.CategoryRequestDto;
import com.appcenter.todo_list.dto.response.CategoryResponseDto;
import com.appcenter.todo_list.entity.Category;
import com.appcenter.todo_list.entity.User;
import com.appcenter.todo_list.exception.CustomException;
import com.appcenter.todo_list.exception.ErrorCode;
import com.appcenter.todo_list.repository.CategoryRepository;
import com.appcenter.todo_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        return CategoryResponseDto.entityToDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(CategoryResponseDto::entityToDto).collect(Collectors.toList());
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (exitsCategoryByName(categoryRequestDto.getName())) {
            throw new CustomException(ErrorCode.DUPLICATE_CATEGORY);
        }

        Category category = CategoryRequestDto.dtoToEntity(categoryRequestDto, user);

        Category savedCategory = categoryRepository.save(category);

        return CategoryResponseDto.entityToDto(savedCategory);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Category findCategory = categoryRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Category updatedCategory = findCategory.update(categoryRequestDto);

        return CategoryResponseDto.entityToDto(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category findCategory = categoryRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryRepository.delete(findCategory);
    }

    private Boolean exitsCategoryByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
