package com.sparta.barointernjava.user.domain.repository;

import com.sparta.barointernjava.user.domain.model.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);
}
