package org.todo.todo.domain.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.todo.domain.todo.dto.req.*;
import org.todo.todo.domain.todo.service.TodoService;
import org.todo.todo.domain.todo.dto.res.ResponseDto;
import org.todo.todo.domain.todo.dto.res.TodoResponseDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "투두 생성하기")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "투두 생성 성공"),
        @ApiResponse(responseCode = "400", description = "투두 생성 실패")
    })
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> saveTodo(@RequestParam Long userId,
                                                      @Valid@RequestBody TodoDto request){

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(todoService.saveTodo(userId, request), "투두 생성 성공"));
    }

    @Operation(summary = "투두 삭제하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "투두 삭제 실패 : 권한 없음")
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto<Boolean>> deleteTodo(@RequestParam Long userId,
                                                           @Valid @RequestBody TodoDeleteRequestDto request){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.deleteTodo(userId, request), "투두 삭제 성공"));
    }

    @Operation(summary = "투두 전체 수정하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 수정 성공"),
            @ApiResponse(responseCode = "403", description = "투두 수정 실패 : 권한 없음")
    })
    @PutMapping
    public ResponseEntity<ResponseDto<Long>> updateTodo(@RequestParam Long userId,
                                                           @Valid@RequestBody TodoUpdateRequestDto request){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.updateTodo(userId, request), request.getTitle()));
    }

    @Operation(summary = "투두 완료여부 수정하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 완료여부 수정 성공"),
            @ApiResponse(responseCode = "403", description = "투두 완료여부 수정 실패 : 권한 없음")
    })
    @PutMapping("/complete")
    public ResponseEntity<ResponseDto<Boolean>> updateCompletedTodo(@RequestParam Long userId,
                                                                    @Valid@RequestBody TodoCompletedRequestDto request){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.updateCompletedTodo(userId, request), request.getTodoId().toString()));
    }

    @Operation(summary = "전체 투두 불러오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "투두 가져오기 성공"),
            @ApiResponse(responseCode = "403", description = "투두 가져오기 실패 : 권한 없음")
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllTodo(@RequestParam Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.getAllTodo(userId), "모든 투두 조회"));
    }

    @Operation(summary = "특정 날짜 투두 불러오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일별 투두 가져오기 성공"),
            @ApiResponse(responseCode = "403", description = "일별 투두 가져오기 실패 : 권한 없음")
    })
    @GetMapping("/date")
    public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getDayTodo(@RequestParam Long userId,
                                                                         @Valid @RequestBody DayTodoRequestDto request){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.getDayTodo(userId, request), request.getRequestDate().toString() + " 투두 조회"));
    }
}