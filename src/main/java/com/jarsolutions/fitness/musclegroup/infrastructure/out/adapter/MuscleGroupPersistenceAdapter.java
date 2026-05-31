package com.jarsolutions.fitness.musclegroup.infrastructure.out.adapter;

import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupAlreadyExistsException;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.domain.port.MuscleGroupRepositoryPort;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.mapper.MuscleGroupMapper;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence.MuscleGroupJpaEntity;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence.MuscleGroupJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupPersistenceAdapter implements MuscleGroupRepositoryPort {
  private final MuscleGroupJpaRepository jpaRepository;
  private final MuscleGroupMapper mapper;

  public MuscleGroupPersistenceAdapter(
      MuscleGroupJpaRepository muscleGroupJpaRepository, MuscleGroupMapper mapper) {
    this.jpaRepository = muscleGroupJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public MuscleGroup save(MuscleGroup muscleGroup) {
    try {
      MuscleGroupJpaEntity muscleGroupToSave = mapper.toEntity(muscleGroup);
      MuscleGroupJpaEntity savedMuscleGroup = jpaRepository.save(muscleGroupToSave);

      return mapper.toDomain(savedMuscleGroup);
    } catch (DataIntegrityViolationException di) {
      throw new MuscleGroupAlreadyExistsException("A muscle group already exists with that name");
    }
  }

  @Override
  public Optional<MuscleGroup> findById(Long id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<MuscleGroup> findByName(String name) {
    return jpaRepository.findByName(name).map(mapper::toDomain);
  }

  @Override
  public List<MuscleGroup> findAll() {
    return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
  }

  @Override
  public void delete(Long id) {
    jpaRepository.deleteById(id);
  }
}
