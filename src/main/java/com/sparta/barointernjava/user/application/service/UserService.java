package com.sparta.barointernjava.user.application.service;

import com.sparta.barointernjava.user.application.dto.CreateUserCommand;
import com.sparta.barointernjava.user.application.dto.UserResponse;
import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse signup(CreateUserCommand command) {
        User user = command.toEntity();
        User savedUser = userRepository.save(user);

        return UserResponse.fromEntity(savedUser);
    }
}
