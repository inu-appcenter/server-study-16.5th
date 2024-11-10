package com.appcenter.todo_list.controller;

import com.appcenter.todo_list.dto.request.TaskRequestDto;
import com.appcenter.todo_list.dto.response.TaskResponseDto;
import com.appcenter.todo_list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@Tag(name = "Task API", description = "Task에 대한 설명입니다.")
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Tasks 조회", description = "1. 파라미터로 categoryId, userId를 넣지 않으면 전부조회" +
            "2. 파라미터로 categoryId만 넣으면 categoryId와 관련된 tasks만 조회" + "3. 파라미터로 userId만 넣으면 userId와 관련된 tasks만 조회" + "4. 둘 다 넣으면 모두 관련된 tasks를 조회")
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasksByCategoryIdAndUserId(
            @RequestParam(defaultValue = "0", name = "categoryId") Long categoryId, @RequestParam(defaultValue = "0", name = "userId") Long userId) {
        return ResponseEntity.status(OK).body(taskService.getTasksByCategoryIdAndUserId(categoryId, userId));
    }

    @Operation(summary = "Task 조회", description = "Task id를 Path로 받아 Task 조회")
    @ApiResponse(responseCode = "200", description = "Task 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(OK).body(taskService.getTaskById(id));
    }

    @Operation(summary = "Task 생성", description = "User id를 Path로 받아 Task 생성")
    @ApiResponse(responseCode = "201", description = "Task 생성 성공")
    @PostMapping("/users/{userId}")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto, @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(OK).body(taskService.createTask(userId, taskRequestDto));
    }

    @Operation(summary = "Task 수정", description = "Task id를 Path로 받아 Task 수정")
    @ApiResponse(responseCode = "200", description = "Task 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable(name = "id") Long id, @RequestBody TaskRequestDto taskRequestDto) {
        return ResponseEntity.status(OK).body(taskService.updateTask(id, taskRequestDto));
    }

    @Operation(summary = "Task 삭제", description = "Task id를 Path로 받아 Task 삭제")
    @ApiResponse(responseCode = "204", description = "Task 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(name = "id") Long id) {
        taskService.deleteTask(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

}
