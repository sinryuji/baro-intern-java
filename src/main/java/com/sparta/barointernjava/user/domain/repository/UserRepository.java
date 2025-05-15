package com.sparta.barointernjava.user.domain.repository;

import com.sparta.barointernjava.user.domain.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    Page<User> findAll(Pageable pageable);
}
