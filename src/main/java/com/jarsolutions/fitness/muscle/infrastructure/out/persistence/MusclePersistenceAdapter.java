package com.jarsolutions.fitness.muscle.infrastructure.out.persistence;

import com.jarsolutions.fitness.muscle.domain.Muscle;
import com.jarsolutions.fitness.muscle.domain.port.MuscleRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MusclePersistenceAdapter implements MuscleRepository {

  private final MuscleJpaRepository muscleJpaRepository;

  public MusclePersistenceAdapter(MuscleJpaRepository muscleJpaRepository) {
    this.muscleJpaRepository = muscleJpaRepository;
  }

  @Override
  public Muscle save(Muscle muscle) {
    MuscleJpaEntity muscleToSave = toEntity(muscle);
    MuscleJpaEntity savedMuscle = muscleJpaRepository.save(muscleToSave);

    return toDomain(savedMuscle);
  }

  @Override
  public Optional<Muscle> findById(Long id) {
    Optional<MuscleJpaEntity> muscleEntity = muscleJpaRepository.findById(id);
    return muscleEntity.map(this::toDomain);
  }

  @Override
  public Optional<Muscle> findByName(String name) {
    Optional<MuscleJpaEntity> muscleEntity = muscleJpaRepository.findByName(name);
    return muscleEntity.map(this::toDomain);
  }

  @Override
  public boolean existsByName(String name) {
    return muscleJpaRepository.existsByName(name);
  }

  private Muscle toDomain(MuscleJpaEntity muscleEntity) {
    return new Muscle(muscleEntity.getId(), muscleEntity.getName());
  }

  private MuscleJpaEntity toEntity(Muscle muscle) {
    return new MuscleJpaEntity(muscle.getId(), muscle.getName());
  }
}
