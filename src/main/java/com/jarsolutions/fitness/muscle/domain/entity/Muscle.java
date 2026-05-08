package com.jarsolutions.fitness.muscle.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Muscle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  public Muscle(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Muscle [id: " + id + "; name: " + name + ";]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Muscle)) return false;
    return id != null && id.equals(((Muscle) o).getId());
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
