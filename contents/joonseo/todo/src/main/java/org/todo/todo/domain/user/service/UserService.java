package org.todo.todo.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.todo.todo.domain.user.dto.req.UserDto;
import org.todo.todo.domain.user.entity.UserEntity;
import org.todo.todo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long createUser(UserDto userDto){
        UserEntity user = UserEntity.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .build();

        return userRepository.save(user).getId();
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
