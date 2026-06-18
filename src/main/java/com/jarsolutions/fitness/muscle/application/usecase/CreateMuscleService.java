package com.jarsolutions.fitness.muscle.application.usecase;

import com.jarsolutions.fitness.muscle.domain.exception.MuscleAlreadyExistsException;
import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.domain.port.in.command.CreateMuscleCommand;
import com.jarsolutions.fitness.muscle.domain.port.in.usecase.CreateMuscleUseCase;
import com.jarsolutions.fitness.muscle.domain.port.out.repository.MuscleRepository;

public class CreateMuscleService implements CreateMuscleUseCase {
  private final MuscleRepository repository;

  public CreateMuscleService(MuscleRepository repository) {
    this.repository = repository;
  }

  @Override
  public Muscle create(CreateMuscleCommand command) {
    String muscleName = command.name();
    boolean muscleExists = repository.existsByName(muscleName);
    if (muscleExists) {
      throw new MuscleAlreadyExistsException("The muscle " + muscleName + " already exists");
    }
    Muscle muscle = new Muscle(muscleName);
    return repository.create(muscle);
  }
}
