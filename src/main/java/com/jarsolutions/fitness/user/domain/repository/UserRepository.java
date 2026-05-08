package com.jarsolutions.fitness.user.domain.repository;

import com.jarsolutions.fitness.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
