package com.jarsolutions.fitness.muscle.infrastructure.out.persistence;

import com.jarsolutions.fitness.common.infrastructure.persistence.BaseJpaEntity;
import com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence.MuscleGroupJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "muscle")
public class MuscleJpaEntity extends BaseJpaEntity<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "muscle_group_id", nullable = false)
  private MuscleGroupJpaEntity muscleGroup;

  protected MuscleJpaEntity() {}

  public MuscleJpaEntity(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "Muscle [id: " + id + "; name: " + name + ";]";
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
