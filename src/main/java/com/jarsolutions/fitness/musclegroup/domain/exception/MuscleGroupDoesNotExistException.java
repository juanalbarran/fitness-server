package com.jarsolutions.fitness.musclegroup.domain.exception;

public class MuscleGroupDoesNotExistException extends RuntimeException {
  public MuscleGroupDoesNotExistException(String message) {
    super(message);
  }
}
