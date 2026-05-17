package com.jarsolutions.fitness.muscle.infrastructure.out.adapter;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.muscle.domain.port.MuscleRepository;
import com.jarsolutions.fitness.muscle.infrastructure.out.mapper.MuscleMapper;
import com.jarsolutions.fitness.muscle.infrastructure.out.persistence.MuscleJpaEntity;
import com.jarsolutions.fitness.muscle.infrastructure.out.persistence.MuscleJpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MusclePersistenceAdapter implements MuscleRepository {

  private final MuscleJpaRepository muscleJpaRepository;
  private final MuscleMapper mapper;

  public MusclePersistenceAdapter(MuscleJpaRepository muscleJpaRepository, MuscleMapper mapper) {
    this.muscleJpaRepository = muscleJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Muscle save(Muscle muscle) {
    MuscleJpaEntity muscleToSave = mapper.toEntity(muscle);
    MuscleJpaEntity savedMuscle = muscleJpaRepository.save(muscleToSave);

    return mapper.toDomain(savedMuscle);
  }

  @Override
  public Optional<Muscle> findById(Long id) {
    return muscleJpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Muscle> findByName(String name) {
    return muscleJpaRepository.findByName(name).map(mapper::toDomain);
  }

  @Override
  public boolean existsByName(String name) {
    return muscleJpaRepository.existsByName(name);
  }
}
