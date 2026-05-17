package com.jarsolutions.fitness.musclegroup.infrastructure.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateMuscleGroupRequest(@NotBlank String name) {}
