package com.jarsolutions.fitness.musclegroup.application.port.in;

import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;

public interface UpdateMuscleGroupUseCase {
  MuscleGroup updateMuscleGroup(Long id, UpdateMuscleGroupCommand command);
}
