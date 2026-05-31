package com.jarsolutions.fitness.musclegroup.application.port.in;

import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import java.util.List;
import java.util.Optional;

public interface GetMuscleGroupUseCase {
  Optional<MuscleGroup> getMuscleGroup(Long id);

  Optional<MuscleGroup> getMuscleGroupByName(String name);

  List<MuscleGroup> getAllMuscleGroups();
}
