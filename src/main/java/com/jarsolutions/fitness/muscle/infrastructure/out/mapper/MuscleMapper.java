package com.jarsolutions.fitness.muscle.infrastructure.out.mapper;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.infrastructure.out.persistence.MuscleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MuscleMapper {

  public Muscle toDomain(MuscleJpaEntity jpaEntity) {
    return new Muscle(jpaEntity.getId(), jpaEntity.getName());
  }

  public MuscleJpaEntity toEntity(Muscle domain) {
    return new MuscleJpaEntity(domain.getId(), domain.getName());
  }
}
