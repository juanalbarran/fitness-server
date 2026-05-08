package com.jarsolutions.fitness.user.domain.repository;

import com.jarsolutions.fitness.user.domain.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {}
