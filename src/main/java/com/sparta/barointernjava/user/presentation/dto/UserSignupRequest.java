package com.sparta.barointernjava.user.presentation.dto;

import com.sparta.barointernjava.user.application.dto.CreateUserCommand;
import com.sparta.barointernjava.user.domain.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignupRequest {

    @NotBlank(message = "username은 필수입니다.")
    @Size(min = 5, max = 20, message = "username은 5글자 이상, 20글자 이하여야 합니다.")
    private String username;
    @NotBlank(message = "password는 필수입니다.")
    @Size(min = 10, max = 20, message = "password는 10글자 이상, 20글자 이하여야 합니다.")
    private String password;
    @NotBlank(message = "nickname은 필수입니다.")
    @Size(min = 3, max = 10, message = "nickname은 3글자 이상, 10글자 이하여야 합니다.")
    private String nickname;

    public CreateUserCommand toApplicationDto() {
        return CreateUserCommand.builder()
            .username(username)
            .password(password)
            .nickname(nickname)
            .role(UserRole.USER)
            .build();
    }
}
