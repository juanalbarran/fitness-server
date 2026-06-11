package com.jarsolutions.fitness.musclegroup.infrastructure.out.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.jarsolutions.fitness.muscle.infrastructure.out.mapper.MuscleMapper;
import com.jarsolutions.fitness.muscle.infrastructure.out.persistence.MuscleJpaEntity;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence.MuscleGroupJpaEntity;
import java.util.List;
import org.junit.jupiter.api.Test;

class MuscleGroupMapperTest {

  private final MuscleGroupMapper mapper = new MuscleGroupMapper(new MuscleMapper());

  @Test
  void toDomain_withEmptyMuscles_mapsIdAndName() {
    MuscleGroupJpaEntity entity = new MuscleGroupJpaEntity(1L, "Chest");

    MuscleGroup domain = mapper.toDomain(entity);

    assertThat(domain.getId()).isEqualTo(1L);
    assertThat(domain.getName()).isEqualTo("Chest");
    assertThat(domain.getMuscles()).isEmpty();
  }

  @Test
  void toDomain_withMuscles_mapsMusclesList() {
    MuscleGroupJpaEntity entity = new MuscleGroupJpaEntity(1L, "Chest");
    entity.getMuscles().add(new MuscleJpaEntity(10L, "Pec Major"));

    MuscleGroup domain = mapper.toDomain(entity);

    assertThat(domain.getMuscles()).hasSize(1);
    assertThat(domain.getMuscles().get(0).getId()).isEqualTo(10L);
    assertThat(domain.getMuscles().get(0).getName()).isEqualTo("Pec Major");
  }

  @Test
  void toEntity_setsIdToNullAndMapsName() {
    MuscleGroup domain = new MuscleGroup(1L, "Chest", List.of());

    MuscleGroupJpaEntity entity = mapper.toEntity(domain);

    assertThat(entity.getId()).isNull();
    assertThat(entity.getName()).isEqualTo("Chest");
  }
}
