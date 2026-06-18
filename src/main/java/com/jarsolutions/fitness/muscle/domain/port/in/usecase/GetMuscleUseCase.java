package com.jarsolutions.fitness.muscle.domain.port.in.usecase;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import java.util.List;

public interface GetMuscleUseCase {
  Muscle getMuscleById(Long id);

  Muscle getMuscleByName(String name);

  List<Muscle> getMuscles();

  boolean existsById(Long id);

  boolean existsByName(String name);
}
