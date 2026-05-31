package com.jarsolutions.fitness.musclegroup.application.port.in;

import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;

public interface CreateMuscleGroupUseCase {
  MuscleGroup createMuscleGroup(CreateMuscleGroupCommand command);
}
