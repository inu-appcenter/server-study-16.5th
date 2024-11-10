package com.appcenter.todo_list.service;

import com.appcenter.todo_list.dto.request.TaskRequestDto;
import com.appcenter.todo_list.dto.response.TaskResponseDto;
import com.appcenter.todo_list.entity.Category;
import com.appcenter.todo_list.entity.Task;
import com.appcenter.todo_list.entity.User;
import com.appcenter.todo_list.repository.CategoryRepository;
import com.appcenter.todo_list.repository.TaskRepository;
import com.appcenter.todo_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();

        return TaskResponseDto.entityToDto(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getTasksByCategoryId(Long categoryId) {
        List<Task> findTasks = taskRepository.findByCategoryId(categoryId);

        return findTasks.stream().map(task -> TaskResponseDto.entityToDto(task)).collect(
                Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getTasksByUserId(Long userId) {
        List<Task> findTasks = taskRepository.findByUserId(userId);

        return findTasks.stream().map(task -> TaskResponseDto.entityToDto(task)).collect(
                Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllTasks() {
        List<Task> findTasks = taskRepository.findAll();

        return findTasks.stream().map(task -> TaskResponseDto.entityToDto(task)).collect(Collectors.toList());
    }

    public TaskResponseDto createTask(Long userId, TaskRequestDto taskRequestDto) {
        Category category = categoryRepository.findById(taskRequestDto.getCategoryId()).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        Task task = TaskRequestDto.dtoToEntity(taskRequestDto, category, user);

        Task savedTask = taskRepository.save(task);

        return TaskResponseDto.entityToDto(savedTask);
    }

    public TaskResponseDto updateTask(Long taskId, TaskRequestDto taskRequestDto) {
        Category category = categoryRepository.findById(taskRequestDto.getCategoryId()).orElseThrow();

        Task task = taskRepository.findById(taskId).orElseThrow();
        Task updatedTask = task.update(taskRequestDto, category);

        return TaskResponseDto.entityToDto(updatedTask);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();

        taskRepository.delete(task);
    }

    public List<TaskResponseDto> getTasksByCategoryIdAndUserId(Long categoryId, Long userId) {
        log.info("{}, {}", categoryId, userId);

        if (categoryId == 0 && userId == 0) {
            log.info("all");
            return getAllTasks();
        } else if (categoryId == 0) {
            return getTasksByUserId(userId);
        } else if (userId == 0) {
            return getTasksByCategoryId(categoryId);
        }

        List<Task> task = taskRepository.findByCategoryIdAndUserId(categoryId, userId);
        return task.stream().map(TaskResponseDto::entityToDto).collect(Collectors.toList());
    }
}
