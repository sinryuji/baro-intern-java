package com.sparta.barointernjava.user.presentation.dto;

import com.sparta.barointernjava.user.application.dto.LoginCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "username은 필수입니다.")
    @Size(min = 5, max = 20, message = "username은 5글자 이상, 20글자 이하여야 합니다.")
    private String username;
    @NotBlank(message = "password는 필수입니다.")
    @Size(min = 10, max = 20, message = "password는 10글자 이상, 20글자 이하여야 합니다.")
    private String password;

    public LoginCommand toApplicationDto() {
        return LoginCommand.builder()
            .username(username)
            .password(password)
            .build();
    }
}
