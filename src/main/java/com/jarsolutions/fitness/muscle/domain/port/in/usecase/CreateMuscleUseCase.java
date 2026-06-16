package com.jarsolutions.fitness.muscle.domain.port.in.usecase;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.domain.port.in.command.CreateMuscleCommand;

public interface CreateMuscleUseCase {
  Muscle create(CreateMuscleCommand command);
}
