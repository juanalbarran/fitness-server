package com.jarsolutions.fitness.muscle.application.usecase;

import com.jarsolutions.fitness.muscle.domain.exception.MuscleDoesNotExistsException;
import com.jarsolutions.fitness.muscle.domain.port.in.usecase.DeleteMuscleUseCase;
import com.jarsolutions.fitness.muscle.domain.port.out.repository.MuscleRepository;

public class DeleteMuscleService implements DeleteMuscleUseCase {
  private final MuscleRepository repository;

  public DeleteMuscleService(MuscleRepository repository) {
    this.repository = repository;
  }

  @Override
  public void deleteById(Long id) {
    boolean exists = repository.existsById(id);
    if (!exists) {
      throw new MuscleDoesNotExistsException(
          "Can not delete the muscle with the id " + id + "because it does not exists");
    }
    repository.deleteById(id);
  }

  @Override
  public void deleteByName(String name) {
    boolean exists = repository.existsByName(name);
    if (!exists) {
      throw new MuscleDoesNotExistsException(
          "Can not delete the muscle " + name + " because it does not exists.");
    }
    repository.deleteByName(name);
  }
}
