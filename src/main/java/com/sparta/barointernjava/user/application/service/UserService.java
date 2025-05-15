package com.sparta.barointernjava.user.application.service;

import com.sparta.barointernjava.common.exception.BadRequestException;
import com.sparta.barointernjava.common.exception.UnauthorizedException;
import com.sparta.barointernjava.common.jwt.JwtProvider;
import com.sparta.barointernjava.user.application.dto.CreateUserCommand;
import com.sparta.barointernjava.user.application.dto.LoginCommand;
import com.sparta.barointernjava.user.application.dto.UserResponse;
import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public UserResponse signup(CreateUserCommand command) {
        if (userRepository.findByUsername(command.getUsername()).isPresent()) {
            throw new BadRequestException("이미 존재하는 username입니다!");
        }
        User user = command.toEntity(bCryptPasswordEncoder.encode(command.getPassword()));
        User savedUser = userRepository.save(user);

        return UserResponse.fromEntity(savedUser);
    }

    @Transactional(readOnly = true)
    public String login(LoginCommand command) {
        User user = userRepository.findByUsername(command.getUsername())
            .orElseThrow(() -> new BadRequestException("존재하지 않은 아이디 입니다!"));

        if (!bCryptPasswordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 틀렸습니다!");
        }

        return jwtProvider.generateAccessToken(user.getUsername(), user.getRole().getAuthority());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BadRequestException("존재하지 않은 유저 입니다!"));

        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getUserList(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserResponse::fromEntity);
    }
}
