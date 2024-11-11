package org.todo.todo.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todo.todo.domain.todo.dto.res.ResponseDto;
import org.todo.todo.domain.user.dto.req.UserDto;
import org.todo.todo.domain.user.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "유저 생성 성공"),
            @ApiResponse(responseCode = "400", description = "중복 이메일 존재")
    })
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createUser(@Valid@RequestBody UserDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(userService.createUser(request), "유저 생성" ));
    }
}
