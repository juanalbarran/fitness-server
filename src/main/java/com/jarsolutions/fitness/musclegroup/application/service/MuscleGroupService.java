package com.jarsolutions.fitness.musclegroup.application.service;

import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.port.in.MuscleGroupUseCases;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.domain.port.MuscleGroupRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MuscleGroupService implements MuscleGroupUseCases {

  private final MuscleGroupRepository repository;

  public MuscleGroupService(MuscleGroupRepository repository) {
    this.repository = repository;
  }

  @Override
  public MuscleGroup createMuscleGroup(CreateMuscleGroupCommand command) {
    return repository.save(new MuscleGroup(command.name()));
  }

  @Override
  public MuscleGroup updateMuscleGroup(UpdateMuscleGroupCommand command) {
    MuscleGroup existing =
        repository
            .findById(command.id())
            .orElseThrow(
                () ->
                    new IllegalArgumentException("A muscle group with that name does not exists."));
    existing.setName(command.name());
    return repository.save(existing);
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
  public void deleteMuscleGroup(Long id) {
    repository.delete(id);
  }
}
