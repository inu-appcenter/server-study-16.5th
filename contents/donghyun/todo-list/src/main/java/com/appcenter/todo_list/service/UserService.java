package com.appcenter.todo_list.service;

import com.appcenter.todo_list.dto.request.UserRequestDto;
import com.appcenter.todo_list.dto.response.UserResponseDto;
import com.appcenter.todo_list.entity.User;
import com.appcenter.todo_list.exception.CustomException;
import com.appcenter.todo_list.exception.ErrorCode;
import com.appcenter.todo_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.entityToDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByName(String name) {
        User user = userRepository.findByName(name).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.entityToDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.entityToDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserResponseDto::entityToDto).collect(Collectors.toList());
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        if (existsUserByEmail(userRequestDto.getEmail()) || existsUserByName(userRequestDto.getName())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        }

        User user = UserRequestDto.dtoToEntity(userRequestDto);

        User savedUser = userRepository.save(user);
        return UserResponseDto.entityToDto(savedUser);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        User updatedUser = user.update(userRequestDto);

        return UserResponseDto.entityToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private Boolean existsUserByName(String name) {
        return userRepository.existsByName(name);
    }

}
