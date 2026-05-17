package com.jarsolutions.fitness.musclegroup.application.port.in;

import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import java.util.List;
import java.util.Optional;

public interface MuscleGroupUseCases {
  MuscleGroup createMuscleGroup(CreateMuscleGroupCommand command);

  MuscleGroup updateMuscleGroup(Long id, UpdateMuscleGroupCommand command);

  Optional<MuscleGroup> getMuscleGroup(Long id);

  Optional<MuscleGroup> getMuscleGroupByName(String name);

  List<MuscleGroup> getAllMuscleGroups();

  void deleteMuscleGroup(Long id);
}
