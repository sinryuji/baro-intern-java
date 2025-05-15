package com.sparta.barointernjava.user.application.service;

import com.sparta.barointernjava.common.exception.BadRequestException;
import com.sparta.barointernjava.user.application.dto.CreateUserCommand;
import com.sparta.barointernjava.user.application.dto.UserResponse;
import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserResponse signup(CreateUserCommand command) {
        if (userRepository.findByUsername(command.getUsername()).isPresent()) {
            throw new BadRequestException("이미 존재하는 username입니다!");
        }
        User user = command.toEntity(bCryptPasswordEncoder.encode(command.getPassword()));
        User savedUser = userRepository.save(user);

        return UserResponse.fromEntity(savedUser);
    }
}
