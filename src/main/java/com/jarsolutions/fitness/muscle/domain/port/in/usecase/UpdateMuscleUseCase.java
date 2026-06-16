package com.jarsolutions.fitness.muscle.domain.port.in.usecase;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.domain.port.in.command.UpdateMuscleCommand;

public interface UpdateMuscleUseCase {
  Muscle update(UpdateMuscleCommand command);
}
