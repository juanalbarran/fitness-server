package com.jarsolutions.fitness.musclegroup.domain.model;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import java.util.List;

public class MuscleGroup {
  private final Long id;
  private String name;
  private final List<Muscle> muscles;

  public MuscleGroup(Long id, String name, List<Muscle> muscles) {
    validateName(name);
    this.id = id;
    this.name = name;
    this.muscles = muscles;
  }

  public MuscleGroup(String name) {
    this(null, name, List.of());
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Muscle> getMuscles() {
    return muscles;
  }

  private void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Muscle group name cannot be null or empty");
    }
  }

  public void rename(String name) {
    validateName(name);
    this.name = name;
  }
}
