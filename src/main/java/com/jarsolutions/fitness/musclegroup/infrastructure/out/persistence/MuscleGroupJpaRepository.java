package com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleGroupJpaRepository extends JpaRepository<MuscleGroupJpaEntity, Long> {
  Optional<MuscleGroupJpaEntity> findByName(String name);
}
