package com.jarsolutions.fitness.muscle.domain.port.out.repository;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import java.util.List;
import java.util.Optional;

public interface MuscleRepository {
  Muscle create(Muscle muscle);

  Muscle update(Muscle muscle);

  Optional<Muscle> findById(Long id);

  Optional<Muscle> findByName(String name);

  List<Muscle> findAll();

  boolean existsByName(String name);

  boolean existsById(Long id);

  void deleteById(Long id);

  void deleteByName(String name);
}
