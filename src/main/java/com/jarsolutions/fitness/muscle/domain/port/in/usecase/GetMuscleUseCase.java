package com.jarsolutions.fitness.muscle.domain.port.in.usecase;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import java.util.List;
import java.util.Optional;

public interface GetMuscleUseCase {
  Optional<Muscle> getMuscleById(Long id);

  Optional<Muscle> getMuscleByName(String name);

  List<Muscle> getMuscles();

  boolean existsById(Long id);

  boolean existsByName(String name);
}
