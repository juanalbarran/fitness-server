package com.jarsolutions.fitness.muscle.domain.port.in.usecase;

public interface DeleteMuscleUseCase {
  void deleteById(Long id);

  void deleteByName(String name);
}
