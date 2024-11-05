package com.appcenter.todo_list.service;

import com.appcenter.todo_list.dto.request.UserRequestDto;
import com.appcenter.todo_list.dto.response.UserResponseDto;
import com.appcenter.todo_list.entity.User;
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
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        return UserResponseDto.entityToDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByName(String name) {
        User user = userRepository.findByName(name).orElseThrow();

        return UserResponseDto.entityToDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        return UserResponseDto.entityToDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> UserResponseDto.entityToDto(user)).collect(Collectors.toList());
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = UserRequestDto.dtoToEntity(userRequestDto);

        User savedUser = userRepository.save(user);
        log.info(savedUser.toString());
        return UserResponseDto.entityToDto(savedUser);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow();

        User updatedUser = user.update(userRequestDto);

        return UserResponseDto.entityToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
