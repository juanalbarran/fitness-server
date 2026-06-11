package com.jarsolutions.fitness.musclegroup.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupAlreadyExistsException;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupDoesNotExistException;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.domain.port.MuscleGroupRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MuscleGroupServiceTest {

  @Mock
  private MuscleGroupRepositoryPort repository;

  @InjectMocks
  private MuscleGroupService service;

  @Test
  void createMuscleGroup_callsRepositoryCreateAndReturnsResult() {
    var saved = new MuscleGroup(1L, "Chest", List.of());
    when(repository.create(any(MuscleGroup.class))).thenReturn(saved);

    MuscleGroup result = service.createMuscleGroup(new CreateMuscleGroupCommand("Chest"));

    assertThat(result).isSameAs(saved);
    verify(repository).create(argThat(mg -> mg.getName().equals("Chest")));
  }

  @Test
  void updateMuscleGroup_withNewName_renamesAndReturnsUpdated() {
    var existing = new MuscleGroup(1L, "Chest", List.of());
    var updated = new MuscleGroup(1L, "Back", List.of());
    when(repository.findById(1L)).thenReturn(Optional.of(existing));
    when(repository.existsByName("Back")).thenReturn(false);
    when(repository.update(any(MuscleGroup.class))).thenReturn(updated);

    MuscleGroup result = service.updateMuscleGroup(1L, new UpdateMuscleGroupCommand("Back"));

    assertThat(result).isSameAs(updated);
    verify(repository).update(argThat(mg -> mg.getName().equals("Back")));
  }

  @Test
  void updateMuscleGroup_withSameName_returnsExistingWithoutCallingUpdate() {
    var existing = new MuscleGroup(1L, "Chest", List.of());
    when(repository.findById(1L)).thenReturn(Optional.of(existing));

    MuscleGroup result = service.updateMuscleGroup(1L, new UpdateMuscleGroupCommand("Chest"));

    assertThat(result).isSameAs(existing);
    verify(repository, never()).update(any());
  }

  @Test
  void updateMuscleGroup_withNonExistentId_throwsMuscleGroupDoesNotExistException() {
    when(repository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> service.updateMuscleGroup(999L, new UpdateMuscleGroupCommand("Back")))
        .isInstanceOf(MuscleGroupDoesNotExistException.class);
  }

  @Test
  void updateMuscleGroup_withTakenName_throwsMuscleGroupAlreadyExistsException() {
    var existing = new MuscleGroup(1L, "Chest", List.of());
    when(repository.findById(1L)).thenReturn(Optional.of(existing));
    when(repository.existsByName("Back")).thenReturn(true);

    assertThatThrownBy(
            () -> service.updateMuscleGroup(1L, new UpdateMuscleGroupCommand("Back")))
        .isInstanceOf(MuscleGroupAlreadyExistsException.class);
  }

  @Test
  void deleteMuscleGroup_whenExists_callsRepositoryDelete() {
    when(repository.existsById(1L)).thenReturn(true);

    service.deleteMuscleGroup(1L);

    verify(repository).delete(1L);
  }

  @Test
  void deleteMuscleGroup_whenNotFound_throwsMuscleGroupDoesNotExistException() {
    when(repository.existsById(999L)).thenReturn(false);

    assertThatThrownBy(() -> service.deleteMuscleGroup(999L))
        .isInstanceOf(MuscleGroupDoesNotExistException.class);
    verify(repository, never()).delete(any());
  }

  @Test
  void getMuscleGroup_delegatesToRepositoryFindById() {
    var expected = Optional.of(new MuscleGroup(1L, "Chest", List.of()));
    when(repository.findById(1L)).thenReturn(expected);

    assertThat(service.getMuscleGroup(1L)).isSameAs(expected);
  }

  @Test
  void getMuscleGroupByName_delegatesToRepositoryFindByName() {
    var expected = Optional.of(new MuscleGroup(1L, "Chest", List.of()));
    when(repository.findByName("Chest")).thenReturn(expected);

    assertThat(service.getMuscleGroupByName("Chest")).isSameAs(expected);
  }

  @Test
  void getAllMuscleGroups_delegatesToRepositoryFindAll() {
    var expected = List.of(new MuscleGroup(1L, "Chest", List.of()));
    when(repository.findAll()).thenReturn(expected);

    assertThat(service.getAllMuscleGroups()).isSameAs(expected);
  }
}
