package com.jarsolutions.fitness.musclegroup.infrastructure.out.mapper;

import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence.MuscleGroupJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupMapper {

  public MuscleGroup toDomain(MuscleGroupJpaEntity jpaEntity) {
    return new MuscleGroup(jpaEntity.getId(), jpaEntity.getName());
  }

  public MuscleGroupJpaEntity toEntity(MuscleGroup domain) {
    return new MuscleGroupJpaEntity(domain.getId(), domain.getName());
  }
}
