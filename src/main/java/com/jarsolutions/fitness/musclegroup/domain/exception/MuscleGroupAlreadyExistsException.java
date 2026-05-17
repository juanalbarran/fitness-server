package com.jarsolutions.fitness.musclegroup.domain.exception;

public class MuscleGroupAlreadyExistsException extends RuntimeException {
  public MuscleGroupAlreadyExistsException(String message) {
    super(message);
  }
}
