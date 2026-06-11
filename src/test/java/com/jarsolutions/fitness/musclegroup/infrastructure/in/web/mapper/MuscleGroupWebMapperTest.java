package com.jarsolutions.fitness.musclegroup.infrastructure.in.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.jarsolutions.fitness.muscle.domain.model.Muscle;
import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.request.CreateMuscleGroupRequest;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.request.UpdateMuscleGroupRequest;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.response.MuscleGroupResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

class MuscleGroupWebMapperTest {

  private final MuscleGroupWebMapper mapper = new MuscleGroupWebMapper();

  @Test
  void toCreateMuscleGroupCommand_mapsName() {
    CreateMuscleGroupCommand command =
        mapper.toCreateMuscleGroupCommand(new CreateMuscleGroupRequest("Chest"));

    assertThat(command.name()).isEqualTo("Chest");
  }

  @Test
  void toUpdateMuscleGroupCommand_mapsName() {
    UpdateMuscleGroupCommand command =
        mapper.toUpdateMuscleGroupCommand(new UpdateMuscleGroupRequest("Back"));

    assertThat(command.name()).isEqualTo("Back");
  }

  @Test
  void toMuscleGroupResponse_mapsAllFieldsAndMuscles() {
    var domain = new MuscleGroup(1L, "Chest", List.of(new Muscle(10L, "Pec Major")));

    MuscleGroupResponse response = mapper.toMuscleGroupResponse(domain);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.name()).isEqualTo("Chest");
    assertThat(response.muscles()).hasSize(1);
    assertThat(response.muscles().get(0).id()).isEqualTo(10L);
    assertThat(response.muscles().get(0).name()).isEqualTo("Pec Major");
  }

  @Test
  void toMuscleGroupResponse_withEmptyMuscles_returnsEmptyMusclesList() {
    var domain = new MuscleGroup(1L, "Chest", List.of());

    MuscleGroupResponse response = mapper.toMuscleGroupResponse(domain);

    assertThat(response.muscles()).isEmpty();
  }
}
