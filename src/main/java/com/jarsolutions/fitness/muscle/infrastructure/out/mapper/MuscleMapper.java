package com.jarsolutions.fitness.muscle.infrastructure.out.mapper;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.infrastructure.out.persistence.MuscleJpaEntity;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.mapper.MuscleGroupMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MuscleMapper {
  private final MuscleGroupMapper muscleGroupMapper;

  public MuscleMapper(MuscleGroupMapper muscleGroupMapper) {
    this.muscleGroupMapper = muscleGroupMapper;
  }

  public Muscle toDomain(MuscleJpaEntity jpaEntity) {
    return new Muscle(jpaEntity.getId(), jpaEntity.getName());
  }

  public List<Muscle> toDomain(List<MuscleJpaEntity> jpaEntityList) {
    List<Muscle> muscles = new ArrayList<>();
    return jpaEntityList.stream()
        .map(
            (MuscleJpaEntity jpaEntity) -> {
              Muscle muscle =
                  new Muscle(
                      jpaEntity.getId(),
                      jpaEntity.getName(),
                      muscleGroupMapper.toDomain(jpaEntity.getMuscleGroup()));
              return muscle;
            })
        .toList();
  }

  public MuscleJpaEntity toEntity(Muscle domain) {
    return new MuscleJpaEntity(domain.getId(), domain.getName());
  }
}
