package com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence;

import com.jarsolutions.fitness.musclegroup.domain.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.domain.port.MuscleGroupRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MuscleGroupPersistenceAdapter implements MuscleGroupRepository {
  private final MuscleGroupJpaRepository jpaRepository;

  public MuscleGroupPersistenceAdapter(MuscleGroupJpaRepository muscleGroupJpaRepository) {
    this.jpaRepository = muscleGroupJpaRepository;
  }

  @Override
  public MuscleGroup save(MuscleGroup muscleGroup) {
    MuscleGroupJpaEntity muscleGroupToSave = toEntity(muscleGroup);
    MuscleGroupJpaEntity savedMuscleGroup = jpaRepository.save(muscleGroupToSave);

    return toDomain(savedMuscleGroup);
  }

  @Override
  public Optional<MuscleGroup> findById(Long id) {
    return jpaRepository.findById(id).map(this::toDomain);
  }

  private MuscleGroup toDomain(MuscleGroupJpaEntity entity) {
    return new MuscleGroup(entity.getId(), entity.getName());
  }

  private MuscleGroupJpaEntity toEntity(MuscleGroup domain) {
    return new MuscleGroupJpaEntity(domain.getId(), domain.getName());
  }
}
