package org.todo.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        userPresent(userId);
        TodoEntity todo = findTodo(todoDeleteRequestDto.getTodoId());

        checkAuthorization(todo, userId);
        todoRepository.delete(todo);
        return true;

    }

    public Long updateTodo(Long userId, TodoUpdateRequestDto todoUpdateRequestDto){
        userPresent(userId);
        TodoEntity todo = findTodo(todoUpdateRequestDto.getTodoId());

        checkAuthorization(todo, userId);
        todo.updateTodo(todoUpdateRequestDto.getTitle(), todoUpdateRequestDto.getDescription(), todoUpdateRequestDto.getDueDate(), todoUpdateRequestDto.getPriority());

        return todo.getId();
    }

    public Boolean updateCompletedTodo(Long userId, TodoCompletedRequestDto todoCompletedRequestDto){
        userPresent(userId);
        TodoEntity todo = findTodo(todoCompletedRequestDto.getTodoId());

        checkAuthorization(todo, userId);
        todo.updateCompleted();

        return true;
    }

    public List<TodoResponseDto> getAllTodo(Long userId){
        userPresent(userId);

       return todoRepository.findAllByUserId(userId).stream()
               .map(TodoResponseDto::from)
               .collect(Collectors.toList());
    }

    public List<TodoResponseDto> getDayTodo(Long userId, DayTodoRequestDto dayTodoRequestDto){
        userPresent(userId);
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

    public void userPresent(Long userId){
        if(!userRepository.existsById(userId))
            throw new RestApiException(CustomErrorCode.USER_NOT_FOUND);
    }

    public void checkAuthorization(TodoEntity todo, Long userId){

        Long targetId = todo.getUser().getId();

        if(Objects.equals(userId, targetId))
            throw new RestApiException(CustomErrorCode.PERMISSION_DENIED);
    }
}
