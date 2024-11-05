package com.appcenter.todo_list.controller;

import com.appcenter.todo_list.dto.request.CategoryRequestDto;
import com.appcenter.todo_list.dto.response.CategoryResponseDto;
import com.appcenter.todo_list.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@Tag(name = "Category API", description = "Category에 대한 설명입니다.")
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Category 조회", description = "Category id를 Path로 받아 Category 조회")
    @ApiResponse(responseCode = "200", description = "Category 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(OK).body(categoryService.getCategoryById(id));
    }

    @Operation(summary = "모든 Category 조회", description = "모든 Category 조회")
    @ApiResponse(responseCode = "200", description = "모든 Category 조회 성공")
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.status(OK).body(categoryService.getAllCategories());
    }

    @Operation(summary = "Category 생성", description = "User id를 Path로 받아 Category 생성")
    @ApiResponse(responseCode = "201", description = "Category 생성 성공")
    @PostMapping("/{userId}")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto requestDto, @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(CREATED).body(categoryService.createCategory(requestDto, userId));
    }

    @Operation(summary = "Category 수정", description = "Category id를 Path로 받아 Category 수정")
    @ApiResponse(responseCode = "200", description = "Category 수정 성공")
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory (@RequestBody CategoryRequestDto requestDto, @PathVariable(name = "id") Long id) {
        return ResponseEntity.status(OK).body(categoryService.updateCategory(id, requestDto));
    }

    @Operation(summary = "Category 삭제", description = "Category id를 Path로 받아 Category 삭제")
    @ApiResponse(responseCode = "204", description = "Category 조회 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }
}
