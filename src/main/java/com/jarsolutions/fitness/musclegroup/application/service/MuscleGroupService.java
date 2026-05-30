package com.jarsolutions.fitness.musclegroup.application.service;

import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.port.in.MuscleGroupUseCases;
import com.jarsolutions.fitness.musclegroup.domain.exception.*;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.domain.port.MuscleGroupRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MuscleGroupService implements MuscleGroupUseCases {

  private final MuscleGroupRepository repository;

  public MuscleGroupService(MuscleGroupRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public MuscleGroup createMuscleGroup(CreateMuscleGroupCommand command) {
    String name = command.name();
    boolean exists = repository.existsByName(name);
    if (exists)
      throw new MuscleGroupAlreadyExistsException("A muscle group already exists with that name.");
    return repository.save(new MuscleGroup(name));
  }

  @Override
  @Transactional
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
    boolean exist = repository.existsByName(name);
    if (exist)
      throw new MuscleGroupAlreadyExistsException("A muscle group with that name already exists.");
    muscleGroup.rename(name);
    return repository.save(muscleGroup);
  }

  @Override
  public Optional<MuscleGroup> getMuscleGroup(Long id) {
    return repository.findById(id);
  }

  @Override
  public Optional<MuscleGroup> getMuscleGroupByName(String name) {
    return repository.findByName(name);
  }

  @Override
  public List<MuscleGroup> getAllMuscleGroups() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public void deleteMuscleGroup(Long id) {
    boolean exist = repository.existsById(id);
    if (!exist)
      throw new MuscleGroupDoesNotExistException(
          "A muscle group with the id: " + id + " does not exist.");
    repository.delete(id);
  }
}
