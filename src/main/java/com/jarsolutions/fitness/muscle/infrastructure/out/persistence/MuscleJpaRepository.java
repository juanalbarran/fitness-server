package com.jarsolutions.fitness.muscle.infrastructure.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleJpaRepository extends JpaRepository<MuscleJpaEntity, Long> {
  Optional<MuscleJpaEntity> findByName(String name);

  boolean existsByName(String name);
}
