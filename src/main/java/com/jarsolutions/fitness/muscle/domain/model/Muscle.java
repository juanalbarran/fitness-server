package com.jarsolutions.fitness.muscle.domain.model;

import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;

public class Muscle {
  private Long id;
  private String name;
  private MuscleGroup muscleGroup;

  public Muscle(String name) {
    this.name = name;
  }

  public Muscle(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Muscle(Long id, String name, MuscleGroup muscleGroup) {
    this.id = id;
    this.name = name;
    this.muscleGroup = muscleGroup;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public MuscleGroup getMuscleGroup() {
    return muscleGroup;
  }

  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Muscle name cannot be null or blank");
    }
    this.name = name;
  }
}
