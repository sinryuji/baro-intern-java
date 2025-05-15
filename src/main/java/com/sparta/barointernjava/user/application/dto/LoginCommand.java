package com.sparta.barointernjava.user.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginCommand {

    private String username;
    private String password;
}
