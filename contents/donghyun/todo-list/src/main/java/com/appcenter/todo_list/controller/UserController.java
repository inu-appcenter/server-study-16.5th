package com.appcenter.todo_list.controller;

import com.appcenter.todo_list.dto.request.LoginRequest;
import com.appcenter.todo_list.dto.request.UserRequestDto;
import com.appcenter.todo_list.dto.response.LoginResponse;
import com.appcenter.todo_list.dto.response.UserResponseDto;
import com.appcenter.todo_list.jwt.JwtTokenProvider;
import com.appcenter.todo_list.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@Tag(name = "User API", description = "User에 대한 설명입니다.")
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "모든 User 조회", description = "모든 User 조회")
    @ApiResponse(responseCode = "200", description = "모든 User 조회 성공")
    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.status(OK).body(userService.getAllUsers());
    }

    @Operation(summary = "id로 User 조회", description = "User id를 Path로 받아 User 조회")
    @ApiResponse(responseCode = "200", description = "id로 User 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(OK).body(userService.getUserById(id));

    }

    @Operation(summary = "name으로 User 조회", description = "User name을 Path로 받아 User 조회")
    @ApiResponse(responseCode = "200", description = "name으로 User 조회 성공")
    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponseDto> getUserByName(@PathVariable(name = "name") String name) {
        return ResponseEntity.status(OK).body(userService.getUserByName(name));
    }

    @Operation(summary = "email로 User 조회", description = "User email을 Path로 받아 User 조회")
    @ApiResponse(responseCode = "200", description = "email로 조회 성공")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable(name = "email") String email) {
        return ResponseEntity.status(OK).body(userService.getUserByEmail(email));
    }

    @Operation(summary = "User 수정", description = "User id를 Path로 받아 User 수정")
    @ApiResponse(responseCode = "200", description = "User 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id) {
        return ResponseEntity.status(OK).body(userService.updateUser(id, userRequestDto));
    }

    @Operation(summary = "User 삭제", description = "User id를 Path로 받아 User 삭제")
    @ApiResponse(responseCode = "204", description = "User 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @Operation(summary = "User 생성", description = "User 생성")
    @ApiResponse(responseCode = "201", description = "User 생성 성공")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(CREATED).body(userService.register(userRequestDto));
    }

    @Operation(summary = "로그인", description = "email, password를 받아서 로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);

        return ResponseEntity.status(OK).body(new LoginResponse(token));
    }
}
