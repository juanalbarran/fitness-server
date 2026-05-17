package com.jarsolutions.fitness.musclegroup.infrastructure.out.persistence;

import com.jarsolutions.fitness.common.infrastructure.persistence.BaseJpaEntity;
import com.jarsolutions.fitness.muscle.infrastructure.out.persistence.MuscleJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "muscle_group")
public class MuscleGroupJpaEntity extends BaseJpaEntity<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @OneToMany(mappedBy = "muscleGroup")
  private List<MuscleJpaEntity> muscles;

  protected MuscleGroupJpaEntity() {}

  public MuscleGroupJpaEntity(Long id, String name) {
    this.id = id;
    this.name = name;
    this.muscles = List.of();
  }

  @Override
  public String toString() {
    return "MuscleGroup [id: " + id + "; name: " + name + ";]";
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

  public List<MuscleJpaEntity> getMuscles() {
    return muscles;
  }

  public void setMuscles(List<MuscleJpaEntity> muscles) {
    this.muscles = muscles;
  }
}
