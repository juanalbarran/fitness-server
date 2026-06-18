package com.jarsolutions.fitness.muscle.application.usecase;

import com.jarsolutions.fitness.muscle.domain.exception.MuscleAlreadyExistsException;
import com.jarsolutions.fitness.muscle.domain.exception.MuscleDoesNotExistsException;
import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.domain.port.in.command.UpdateMuscleCommand;
import com.jarsolutions.fitness.muscle.domain.port.in.usecase.UpdateMuscleUseCase;
import com.jarsolutions.fitness.muscle.domain.port.out.repository.MuscleRepository;
import com.jarsolutions.fitness.musclegroup.domain.port.MuscleGroupRepositoryPort;

public class UpdateMuscleService implements UpdateMuscleUseCase {
  private final MuscleRepository muscleRepository;
  private final MuscleGroupRepositoryPort muscleGroupRepository;

  public UpdateMuscleService(MuscleRepository muscleRepository, MuscleGroupRepositoryPort muscleGroupRepository) {
    this.muscleRepository = muscleRepository;
    this.muscleGroupRepository = muscleGroupRepository;
  }

  @Override
  public Muscle update(UpdateMuscleCommand command) {
    Long id = command.id();
    String newMuscleName = command.name();
    Muscle muscle =
        muscleRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new MuscleDoesNotExistsException(
                        "The muscle with the id " + id + " does not exists"));
    String oldMuscleName = muscle.getName();
    boolean muscleNameExists = muscleRepository.existsByName(newMuscleName);
    if ((!newMuscleName.equals(oldMuscleName)) && muscleNameExists) {
        throw new MuscleAlreadyExistsException("Cannot update muscle " + oldMuscleName + " with the new name: " + newMuscleName + ". There is already a muscle with that name.");
    }  
  }

