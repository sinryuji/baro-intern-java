package com.sparta.barointernjava.user.application.dto;

import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.model.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserCommand {

    private String username;
    private String password;
    private String nickname;
    private UserRole role;

    public User toEntity(String encryptedPassword) {
        return User.builder()
            .username(username)
            .password(encryptedPassword)
            .nickname(nickname)
            .role(role)
            .build();
    }
}
