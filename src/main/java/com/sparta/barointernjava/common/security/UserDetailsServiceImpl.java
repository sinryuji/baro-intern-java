package com.sparta.barointernjava.common.security;

import com.sparta.barointernjava.common.exception.NotFoundException;
import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("존재하지 않은 유저입니다!"));

        return new UserDetailsImpl(user);


    }

}
