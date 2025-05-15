package com.sparta.barointernjava.user.domain.repository;

import com.sparta.barointernjava.user.domain.model.User;

public interface UserRepository {

    User save(User user);
}
