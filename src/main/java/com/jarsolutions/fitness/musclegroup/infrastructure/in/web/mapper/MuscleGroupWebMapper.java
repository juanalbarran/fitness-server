package com.jarsolutions.fitness.musclegroup.infrastructure.in.web.mapper;

import com.jarsolutions.fitness.musclegroup.application.command.CreateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.application.command.UpdateMuscleGroupCommand;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.request.CreateMuscleGroupRequest;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.request.UpdateMuscleGroupRequest;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.response.MuscleGroupResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupWebMapper {

  public CreateMuscleGroupCommand toCreateMuscleGroupCommand(CreateMuscleGroupRequest request) {
    return new CreateMuscleGroupCommand(request.name());
  }

  public UpdateMuscleGroupCommand toUpdateMuscleGroupCommand(UpdateMuscleGroupRequest request) {
    return new UpdateMuscleGroupCommand(request.name());
  }

  public MuscleGroupResponse toMuscleGroupResponse(MuscleGroup domain) {
    List<MuscleGroupResponse.MuscleResponse> muscles =
        domain.getMuscles() != null
            ? domain.getMuscles().stream()
                .map(
                    muscle ->
                        new MuscleGroupResponse.MuscleResponse(muscle.getId(), muscle.getName()))
                .toList()
            : List.of();
    return new MuscleGroupResponse(domain.getId(), domain.getName(), muscles);
  }
}
