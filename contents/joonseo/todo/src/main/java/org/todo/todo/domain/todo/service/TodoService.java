package org.todo.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.todo.todo.domain.todo.dto.req.*;
import org.todo.todo.domain.todo.entity.TodoEntity;
import org.todo.todo.domain.todo.repository.TodoRepository;
import org.todo.todo.domain.user.entity.UserEntity;
import org.todo.todo.domain.user.repository.UserRepository;
import org.todo.todo.domain.todo.dto.res.TodoResponseDto;
import org.todo.todo.global.error.code.CustomErrorCode;
import org.todo.todo.global.error.exception.RestApiException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public Long saveTodo(Long userId, TodoDto todoDto){
        UserEntity user = findUser(userId);

        TodoEntity todo = TodoEntity.builder()
                .title(todoDto.getTitle())
                .description(todoDto.getDescription())
                .dueDate(todoDto.getDueDate())
                .priority(todoDto.getPriority())
                .user(user)
                .build();

        return todoRepository.save(todo).getId();
    }

    public Boolean deleteTodo(Long userId, TodoDeleteRequestDto todoDeleteRequestDto){

        TodoEntity todo = findTodo(todoDeleteRequestDto.getTodoId());

        if(checkAuthorization(todo, userId)) {
            todoRepository.delete(todo);
            return true;
        }

        return false;
    }

    public Long updateTodo(Long userId, TodoUpdateRequestDto todoUpdateRequestDto){

        TodoEntity todo = findTodo(todoUpdateRequestDto.getTodoId());

        if(checkAuthorization(todo, userId)){
            todo.updateTodo(todoUpdateRequestDto.getTitle(), todoUpdateRequestDto.getDescription(), todoUpdateRequestDto.getDueDate(), todoUpdateRequestDto.getPriority());
            return todo.getId();
        }

        return -1L;
    }

    public Boolean updateCompletedTodo(Long userId, TodoCompletedRequestDto todoCompletedRequestDto){

        TodoEntity todo = findTodo(todoCompletedRequestDto.getTodoId());

        if(checkAuthorization(todo, userId)) {
            todo.updateCompleted();
            return true;
        }

        return false;
    }

    public List<TodoResponseDto> getAllTodo(Long userId){
       return todoRepository.findAllByUserId(userId).stream()
               .map(TodoResponseDto::from)
               .collect(Collectors.toList());
    }

    public List<TodoResponseDto> getDayTodo(Long userId, DayTodoRequestDto dayTodoRequestDto){
        return todoRepository.findAllByCreatedAtAndUserId(dayTodoRequestDto.getRequestDate(),userId).stream()
                .map(TodoResponseDto::from)
                .collect(Collectors.toList());
    }

    public UserEntity findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PERMISSION_DENIED));
    }

    public TodoEntity findTodo(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.TODO_NOT_FOUND));
    }

    public Boolean checkAuthorization(TodoEntity todo, Long userId){

        Long targetId = todo.getUser().getId();

        return Objects.equals(userId, targetId);
    }
}
