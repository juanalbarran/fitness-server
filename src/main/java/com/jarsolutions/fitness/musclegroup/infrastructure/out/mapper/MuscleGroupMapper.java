package com.jarsolutions.fitness.musclegroup.infrastructure.out.mapper;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.infrastructure.out.mapper.MuscleMapper;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence.MuscleGroupJpaEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupMapper {

  private final MuscleMapper muscleMapper;

  MuscleGroupMapper(MuscleMapper muscleMapper) {
    this.muscleMapper = muscleMapper;
  }

  public MuscleGroup toDomain(MuscleGroupJpaEntity jpaEntity) {
    List<Muscle> muscles = jpaEntity.getMuscles().stream().map(muscleMapper::toDomain).toList();
    return new MuscleGroup(jpaEntity.getId(), jpaEntity.getName(), muscles);
  }

  public MuscleGroupJpaEntity toEntity(MuscleGroup domain) {
    return new MuscleGroupJpaEntity(null, domain.getName());
  }
}
