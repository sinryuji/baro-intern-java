package com.sparta.barointernjava.user.application.dto;

import com.sparta.barointernjava.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

    private String username;
    private String nickname;
    private String role;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
            .username(user.getUsername())
            .nickname(user.getNickname())
            .role(user.getRole().getAuthority())
            .build();
    }
}
