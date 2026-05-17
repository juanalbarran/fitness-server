package com.jarsolutions.fitness.muscle.domain.port;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import java.util.Optional;

public interface MuscleRepository {
  Muscle save(Muscle muscle);

  Optional<Muscle> findById(Long id);

  Optional<Muscle> findByName(String name);

  boolean existsByName(String name);
}
