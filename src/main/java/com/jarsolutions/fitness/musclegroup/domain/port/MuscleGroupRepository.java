package com.jarsolutions.fitness.musclegroup.domain.port;

import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import java.util.List;
import java.util.Optional;

public interface MuscleGroupRepository {
  MuscleGroup save(MuscleGroup muscleGroup);

  Optional<MuscleGroup> findById(Long id);

  Optional<MuscleGroup> findByName(String name);

  List<MuscleGroup> findAll();

  void delete(Long id);

  boolean existsById(Long id);

  boolean existsByName(String name);
}
