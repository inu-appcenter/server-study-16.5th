package com.appcenter.todo_list.controller;

import com.appcenter.todo_list.dto.request.TaskRequestDto;
import com.appcenter.todo_list.dto.response.CategoryResponseDto;
import com.appcenter.todo_list.dto.response.TaskResponseDto;
import com.appcenter.todo_list.service.TaskService;
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

@RestController
@Tag(name = "Task API", description = "Task에 대한 설명입니다.")
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Task 조회", description = "Task id를 Path로 받아 Task 조회")
    @ApiResponse(responseCode = "200", description = "Task 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
    }

    @Operation(summary = "Category에 연관된 Task 조회", description = "Category id를 Path로 받아 연관된 Task 조회")
    @ApiResponse(responseCode = "200", description = "Category에 연관된 Task 조회 성공")
    @GetMapping("/categories/{id}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByCategoryId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasksByCategoryId(id));
    }

    @Operation(summary = "User에 연관된 Task 조회", description = "User id를 Path로 받아 연관된 Task 조회")
    @ApiResponse(responseCode = "200", description = "User에 연관된 Task 조회 성공")
    @GetMapping("/users/{id}")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasksByUserId(id));
    }

    @Operation(summary = "모든 Task 조회", description = "모든 Task 조회")
    @ApiResponse(responseCode = "200", description = "모든 Task 조회 성공")
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    @Operation(summary = "Task 생성", description = "User id를 Path로 받아 Task 생성")
    @ApiResponse(responseCode = "201", description = "Task 생성 성공")
    @PostMapping("/{userId}")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto, @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(userId, taskRequestDto));
    }

    @Operation(summary = "Task 수정", description = "Task id를 Path로 받아 Task 수정")
    @ApiResponse(responseCode = "200", description = "Task 수정 성공")
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable(name = "id") Long id, @RequestBody TaskRequestDto taskRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, taskRequestDto));
    }

    @Operation(summary = "Task 삭제", description = "Task id를 Path로 받아 Task 삭제")
    @ApiResponse(responseCode = "204", description = "Task 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(name = "id") Long id) {
        taskService.deleteTask(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
