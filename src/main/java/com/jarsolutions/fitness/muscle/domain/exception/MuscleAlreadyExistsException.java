package com.jarsolutions.fitness.muscle.domain.exception;

public class MuscleAlreadyExistsException extends RuntimeException {
  public MuscleAlreadyExistsException(String message) {
    super(message);
  }
}
