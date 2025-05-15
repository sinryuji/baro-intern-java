package com.sparta.barointernjava.user.infrastructure.persistence;

import com.sparta.barointernjava.user.domain.model.User;
import com.sparta.barointernjava.user.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends UserRepository, JpaRepository<User, Long> {

}
