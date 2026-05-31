package com.jarsolutions.fitness.musclegroup.application.service;

import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.port.in.CreateMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.DeleteMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.GetMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.UpdateMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.domain.exception.*;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.domain.port.MuscleGroupRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MuscleGroupService
    implements CreateMuscleGroupUseCase,
        UpdateMuscleGroupUseCase,
        GetMuscleGroupUseCase,
        DeleteMuscleGroupUseCase {

  private final MuscleGroupRepositoryPort repository;

  public MuscleGroupService(MuscleGroupRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  public MuscleGroup createMuscleGroup(CreateMuscleGroupCommand command) {
    String name = command.name();
    if (repository.findByName(name).isPresent())
      throw new MuscleGroupAlreadyExistsException("A muscle group already exists with that name.");
    return repository.save(new MuscleGroup(name));
  }

  @Override
  public MuscleGroup updateMuscleGroup(Long id, UpdateMuscleGroupCommand command) {
    String name = command.name();
    MuscleGroup muscleGroup =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new MuscleGroupDoesNotExistException(
                        "A muscle group with that id does not exist."));
    if (name.equals(muscleGroup.getName())) return muscleGroup;
    if (repository.findByName(name).isPresent())
      throw new MuscleGroupAlreadyExistsException("A muscle group already exists with that name.");
    muscleGroup.rename(name);
    return repository.save(muscleGroup);
  }

  @Override
  public void deleteMuscleGroup(Long id) {
    repository
        .findById(id)
        .orElseThrow(
            () ->
                new MuscleGroupDoesNotExistException(
                    "A muscle group with the id: " + id + " does not exist."));
    repository.delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<MuscleGroup> getMuscleGroup(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<MuscleGroup> getMuscleGroupByName(String name) {
    return repository.findByName(name);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MuscleGroup> getAllMuscleGroups() {
    return repository.findAll();
  }
}
