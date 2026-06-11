package com.jarsolutions.fitness.musclegroup.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MuscleGroupTest {

  @Test
  void constructor_withValidName_createsWithNullIdAndEmptyMuscles() {
    MuscleGroup mg = new MuscleGroup("Chest");

    assertThat(mg.getId()).isNull();
    assertThat(mg.getName()).isEqualTo("Chest");
    assertThat(mg.getMuscles()).isEmpty();
  }

  @Test
  void constructor_withNullName_throwsIllegalArgumentException() {
    assertThatThrownBy(() -> new MuscleGroup(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void constructor_withBlankName_throwsIllegalArgumentException() {
    assertThatThrownBy(() -> new MuscleGroup("   "))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void rename_updatesName() {
    MuscleGroup mg = new MuscleGroup("Chest");

    mg.rename("Back");

    assertThat(mg.getName()).isEqualTo("Back");
  }

  @Test
  void rename_withNullName_throwsIllegalArgumentException() {
    MuscleGroup mg = new MuscleGroup("Chest");

    assertThatThrownBy(() -> mg.rename(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void rename_withBlankName_throwsIllegalArgumentException() {
    MuscleGroup mg = new MuscleGroup("Chest");

    assertThatThrownBy(() -> mg.rename("  "))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
