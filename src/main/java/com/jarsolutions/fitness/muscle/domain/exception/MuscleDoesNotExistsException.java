package com.jarsolutions.fitness.muscle.domain.exception;

public class MuscleDoesNotExistsException extends RuntimeException {
  public MuscleDoesNotExistsException(String message) {
    super(message);
  }
}
