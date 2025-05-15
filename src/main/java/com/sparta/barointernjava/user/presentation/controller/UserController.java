package com.sparta.barointernjava.user.presentation.controller;

import com.sparta.barointernjava.user.application.dto.UserResponse;
import com.sparta.barointernjava.user.application.service.UserService;
import com.sparta.barointernjava.user.presentation.dto.AdminSignupRequest;
import com.sparta.barointernjava.user.presentation.dto.LoginRequest;
import com.sparta.barointernjava.user.presentation.dto.TokenResponse;
import com.sparta.barointernjava.user.presentation.dto.UserSignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "일반 유저 회원가입")
    public ResponseEntity<UserResponse> signupUser(@RequestBody @Valid UserSignupRequest request) {
        UserResponse response = userService.signup(request.toApplicationDto());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/admin")
    @Operation(summary = "관리자 회원가입")
    public ResponseEntity<UserResponse> signupAdmin(
        @RequestBody @Valid AdminSignupRequest request) {
        UserResponse response = userService.signup(request.toApplicationDto());

        return ResponseEntity.ok(response);
    }

    @PostMapping("login")
    @Operation(summary = "로그인")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        String token = userService.login(request.toApplicationDto());

        return ResponseEntity.ok(new TokenResponse(token));
    }
}
