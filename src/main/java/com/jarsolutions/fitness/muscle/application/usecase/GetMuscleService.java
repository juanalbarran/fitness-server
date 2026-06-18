package com.jarsolutions.fitness.muscle.application.usecase;

import com.jarsolutions.fitness.muscle.domain.exception.MuscleDoesNotExistsException;
import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.domain.port.in.usecase.GetMuscleUseCase;
import com.jarsolutions.fitness.muscle.domain.port.out.repository.MuscleRepository;
import java.util.List;

public class GetMuscleService implements GetMuscleUseCase {
  private final MuscleRepository repository;

  public GetMuscleService(MuscleRepository repository) {
    this.repository = repository;
  }

  @Override
  public Muscle getMuscleById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new MuscleDoesNotExistsException(
                    "The muscle with the id " + id + " does not exists."));
  }

  @Override
  public Muscle getMuscleByName(String name) {
    return repository
        .findByName(name)
        .orElseThrow(
            () ->
                new MuscleDoesNotExistsException(
                    "The muscle with the name " + name + "does not exists"));
  }

  @Override
  public List<Muscle> getMuscles() {
    return repository.findAll();
  }

  @Override
  public boolean existsById(Long id) {
    return repository.existsById(id);
  }

  @Override
  public boolean existsByName(String name) {
    return repository.existsByName(name);
  }
}
