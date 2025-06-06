package com.sparta.barointernjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.barointernjava.common.exception.BadRequestException;
import com.sparta.barointernjava.common.exception.UnauthorizedException;
import com.sparta.barointernjava.common.jwt.JwtProvider;
import com.sparta.barointernjava.user.application.dto.CreateUserCommand;
import com.sparta.barointernjava.user.application.dto.LoginCommand;
import com.sparta.barointernjava.user.application.dto.UserResponse;
import com.sparta.barointernjava.user.application.service.UserService;
import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.model.UserRole;
import com.sparta.barointernjava.user.domain.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserService userService;

    @Test
    void 회원가입_성공() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        CreateUserCommand command = CreateUserCommand.builder()
            .username("test")
            .password(rawPassword)
            .nickname("test")
            .role(UserRole.USER)
            .build();

        User user = command.toEntity(encodedPassword);

        when(userRepository.findByUsername(command.getUsername())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse userResponse = userService.signup(command);

        assertEquals(user.getUsername(), userResponse.getUsername());
        verify(bCryptPasswordEncoder).encode(rawPassword);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 중복된_아이디로_회원가입하면_예외발생() {
        CreateUserCommand command = CreateUserCommand.builder()
            .username("test")
            .password("password")
            .nickname("test")
            .role(UserRole.USER)
            .build();

        when(userRepository.findByUsername(command.getUsername())).thenReturn(
            Optional.of(new User()));

        assertThrows(BadRequestException.class, () -> userService.signup(command));
        verify(userRepository, never()).save(any());
    }

    @Test
    void 로그인_성공() {
        // given
        String username = "test";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        String expectedToken = "jwt.token.value";

        LoginCommand command = LoginCommand.builder()
            .username(username)
            .password(rawPassword)
            .build();

        User user = User.builder()
            .username(username)
            .password(encodedPassword)
            .role(UserRole.USER)
            .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtProvider.generateAccessToken(username, user.getRole().getAuthority())).thenReturn(
            expectedToken);

        String result = userService.login(command);

        assertEquals(expectedToken, result);
        verify(userRepository).findByUsername(username);
        verify(bCryptPasswordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtProvider).generateAccessToken(username, user.getRole().getAuthority());
    }

    @Test
    void 로그인_실패_비밀번호틀림() {
        // given
        String username = "test";
        String rawPassword = "wrongPassword";
        String encodedPassword = "encodedPassword";

        LoginCommand command = LoginCommand.builder()
            .username(username)
            .password(rawPassword)
            .build();

        User user = User.builder()
            .username(username)
            .password(encodedPassword)
            .role(UserRole.USER)
            .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> userService.login(command));

        verify(userRepository).findByUsername(username);
        verify(bCryptPasswordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtProvider, never()).generateAccessToken(any(), any());
    }
}
