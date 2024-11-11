package org.todo.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.todo.todo.domain.todo.dto.req.*;
import org.todo.todo.domain.todo.entity.TodoEntity;
import org.todo.todo.domain.todo.repository.TodoRepository;
import org.todo.todo.domain.user.entity.UserEntity;
import org.todo.todo.domain.user.repository.UserRepository;
import org.todo.todo.domain.todo.dto.res.TodoResponseDto;

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
        // 삭제하려는 유저와 투두 작성자 일치 여부 확인
        // TODO 에러핸들링 추가
        TodoEntity todo = todoRepository.findById(todoDeleteRequestDto.getTodoId())
                .orElseThrow(null);

        if(checkAuthorization(todo, userId)) {
            todoRepository.delete(todo);
            return true;
        }

        return false;
    }

    public Boolean updateTodo(Long userId, TodoUpdateRequestDto todoUpdateRequestDto){
        // 수정하려는 유저와 투두 작성자 일치 여부 확인
        // TODO 에러핸들링 추가
        TodoEntity todo = todoRepository.findByTitle(todoUpdateRequestDto.getTitle())
                .orElseThrow(null);

        if(checkAuthorization(todo, userId)){
            todo.updateTodo(todoUpdateRequestDto.getTitle(), todoUpdateRequestDto.getDescription(), todoUpdateRequestDto.getDueDate(), todoUpdateRequestDto.getPriority());
            return true;
        }

        return false;
    }

    public Boolean updateCompletedTodo(Long userId, TodoCompletedRequestDto todoCompletedRequestDto){
        // TODO 에러핸들링 추가
        TodoEntity todo = todoRepository.findById(todoCompletedRequestDto.getTodoId())
                .orElseThrow(null);

        if(checkAuthorization(todo, userId)) {
            todo.updateCompleted();
            return true;
        }

        return false;
    }

    public List<TodoResponseDto> getAllTodo(Long userId){
       // TODO 배열이 비어있는 경우 처리
       return todoRepository.findAllByUserId(userId).stream()
               .map(TodoResponseDto::from)
               .collect(Collectors.toList());
    }

    public List<TodoResponseDto> getDayTodo(Long userId, DayTodoRequestDto dayTodoRequestDto){
        // TODO 배열이 비어있는 경우 처리
        return todoRepository.findAllByCreatedAtAndUserId(dayTodoRequestDto.getRequestDate(),userId).stream()
                .map(TodoResponseDto::from)
                .collect(Collectors.toList());
    }

    public UserEntity findUser(Long userId){
        // TODO 에러핸들링 추가
        return userRepository.findById(userId)
                .orElseThrow(null);
    }

    public Boolean checkAuthorization(TodoEntity todo, Long userId){

        Long targetId = todo.getUser().getId();

        return Objects.equals(userId, targetId);
    }
}
