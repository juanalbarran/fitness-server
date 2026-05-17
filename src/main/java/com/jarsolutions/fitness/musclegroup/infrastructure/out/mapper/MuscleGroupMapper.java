package com.jarsolutions.fitness.musclegroup.infrastructure.out.mapper;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence.MuscleGroupJpaEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupMapper {

  public MuscleGroup toDomain(MuscleGroupJpaEntity jpaEntity) {
    List<Muscle> muscles =
        jpaEntity.getMuscles() != null
            ? jpaEntity.getMuscles().stream()
                .map(muscle -> new Muscle(muscle.getId(), muscle.getName()))
                .toList()
            : List.of();
    return new MuscleGroup(jpaEntity.getId(), jpaEntity.getName(), muscles);
  }

  public MuscleGroupJpaEntity toEntity(MuscleGroup domain) {
    return new MuscleGroupJpaEntity(domain.getId(), domain.getName());
  }
}
