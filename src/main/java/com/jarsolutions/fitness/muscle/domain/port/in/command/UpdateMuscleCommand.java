package com.jarsolutions.fitness.muscle.domain.port.in.command;

public record UpdateMuscleCommand(Long id, String name, String muscleGroup) {}
