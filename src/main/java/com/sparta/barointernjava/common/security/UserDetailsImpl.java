package com.sparta.barointernjava.common.security;

import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.model.UserRole;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getRole();
        String authority = role.getAuthority();

        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // 유저 비밀번호 반환
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
