package com.jarsolutions.fitness.musclegroup.domain.model;

public class MuscleGroup {
  private Long id;
  private String name;

  public MuscleGroup(String name) {
    this.name = name;
  }

  public MuscleGroup(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Muscle group name cannot be null or empty");
    }
    this.name = name;
  }
}
