package com.jarsolutions.fitness.musclegroup.infrastructure.in.web.response;

import java.util.List;

public record MuscleGroupResponse(Long id, String name, List<MuscleResponse> muscles) {
  public record MuscleResponse(Long id, String name) {}
}
