package com.jarsolutions.fitness.muscle.domain;

public class Muscle {
  private Long id;
  private String name;

  public Muscle(String name) {
    this.name = name;
  }

  public Muscle(Long id, String name) {
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
      throw new IllegalArgumentException("Muscle name cannot be null or blank");
    }
    this.name = name;
  }
}
