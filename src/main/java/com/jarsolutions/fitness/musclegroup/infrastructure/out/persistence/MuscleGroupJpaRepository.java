package com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MuscleGroupJpaRepository extends JpaRepository<MuscleGroupJpaEntity, Long> {

  @Query(
      "SELECT DISTINCT musclegroup FROM MuscleGroupJpaEntity musclegroup LEFT JOIN FETCH"
          + " musclegroup.muscles")
  List<MuscleGroupJpaEntity> findAll();

  @EntityGraph(attributePaths = "muscles")
  Optional<MuscleGroupJpaEntity> findById(Long id);

  @EntityGraph(attributePaths = "muscles")
  Optional<MuscleGroupJpaEntity> findByName(String name);

  boolean existsByName(String name);
}
