package com.jarsolutions.fitness.musclegroup.domain.model;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import java.util.List;

public class MuscleGroup {
  private Long id;
  private String name;
  private List<Muscle> muscles;

  public MuscleGroup(String name) {
    this.name = name;
    this.muscles = List.of();
  }

  public MuscleGroup(Long id, String name, List<Muscle> muscles) {
    this.id = id;
    this.name = name;
    this.muscles = muscles;
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

  public void setMuscles(List<Muscle> muscles) {
    this.muscles = muscles;
  }

  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Muscle group name cannot be null or empty");
    }
    this.name = name;
  }
}
