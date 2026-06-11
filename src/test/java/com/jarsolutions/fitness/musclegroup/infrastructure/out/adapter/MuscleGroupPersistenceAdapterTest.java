package com.jarsolutions.fitness.musclegroup.infrastructure.out.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jarsolutions.fitness.muscle.infrastructure.out.mapper.MuscleMapper;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupAlreadyExistsException;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupDoesNotExistException;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.mapper.MuscleGroupMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseAutoConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(LiquibaseAutoConfiguration.class)
@Import({MuscleGroupPersistenceAdapter.class, MuscleGroupMapper.class, MuscleMapper.class})
class MuscleGroupPersistenceAdapterTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

  @Autowired
  private MuscleGroupPersistenceAdapter adapter;

  @Test
  void create_savesAndReturnsWithGeneratedId() {
    MuscleGroup result = adapter.create(new MuscleGroup("Chest"));

    assertThat(result.getId()).isNotNull();
    assertThat(result.getName()).isEqualTo("Chest");
    assertThat(result.getMuscles()).isEmpty();
  }

  @Test
  void create_withDuplicateName_throwsMuscleGroupAlreadyExistsException() {
    adapter.create(new MuscleGroup("Chest"));

    assertThatThrownBy(() -> adapter.create(new MuscleGroup("Chest")))
        .isInstanceOf(MuscleGroupAlreadyExistsException.class);
  }

  @Test
  void update_renamesEntityAndReturnsUpdatedDomain() {
    MuscleGroup created = adapter.create(new MuscleGroup("Chest"));

    MuscleGroup result = adapter.update(new MuscleGroup(created.getId(), "Back", List.of()));

    assertThat(result.getName()).isEqualTo("Back");
  }

  @Test
  void update_withNonExistentId_throwsMuscleGroupDoesNotExistException() {
    assertThatThrownBy(() -> adapter.update(new MuscleGroup(999L, "Back", List.of())))
        .isInstanceOf(MuscleGroupDoesNotExistException.class);
  }

  @Test
  void findById_whenFound_returnsOptionalWithMuscleGroup() {
    MuscleGroup created = adapter.create(new MuscleGroup("Chest"));

    Optional<MuscleGroup> result = adapter.findById(created.getId());

    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo("Chest");
  }

  @Test
  void findById_whenNotFound_returnsEmpty() {
    assertThat(adapter.findById(999L)).isEmpty();
  }

  @Test
  @Sql("/sql/musclegroup-with-muscles.sql")
  void findById_loadsMusculesEagerly() {
    Optional<MuscleGroup> result = adapter.findById(100L);

    assertThat(result).isPresent();
    assertThat(result.get().getMuscles()).hasSize(1);
    assertThat(result.get().getMuscles().get(0).getName()).isEqualTo("Pec Major");
  }

  @Test
  void findByName_whenFound_returnsOptional() {
    adapter.create(new MuscleGroup("Chest"));

    Optional<MuscleGroup> result = adapter.findByName("Chest");

    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo("Chest");
  }

  @Test
  void findByName_whenNotFound_returnsEmpty() {
    assertThat(adapter.findByName("Unknown")).isEmpty();
  }

  @Test
  void findAll_returnsAllMuscleGroups() {
    adapter.create(new MuscleGroup("Chest"));
    adapter.create(new MuscleGroup("Back"));

    List<MuscleGroup> all = adapter.findAll();

    assertThat(all).hasSize(2);
    assertThat(all).extracting(MuscleGroup::getName).containsExactlyInAnyOrder("Chest", "Back");
  }

  @Test
  void delete_removesEntityFromRepository() {
    MuscleGroup created = adapter.create(new MuscleGroup("Chest"));

    adapter.delete(created.getId());

    assertThat(adapter.findById(created.getId())).isEmpty();
  }

  @Test
  void existsById_whenFound_returnsTrue() {
    MuscleGroup created = adapter.create(new MuscleGroup("Chest"));

    assertThat(adapter.existsById(created.getId())).isTrue();
  }

  @Test
  void existsById_whenNotFound_returnsFalse() {
    assertThat(adapter.existsById(999L)).isFalse();
  }

  @Test
  void existsByName_whenFound_returnsTrue() {
    adapter.create(new MuscleGroup("Chest"));

    assertThat(adapter.existsByName("Chest")).isTrue();
  }

  @Test
  void existsByName_whenNotFound_returnsFalse() {
    assertThat(adapter.existsByName("Unknown")).isFalse();
  }
}
