package com.jarsolutions.fitness.musclegroup.domain.port;

import com.jarsolutions.fitness.musclegroup.domain.MuscleGroup;
import java.util.Optional;

public interface MuscleGroupRepository {
  MuscleGroup save(MuscleGroup muscleGroup);

  Optional<MuscleGroup> findById(Long id);
}
