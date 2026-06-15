package com.jarsolutions.fitness.common.infrastructure.in.web.exception;

import com.jarsolutions.fitness.common.infrastructure.in.web.response.ErrorResponse;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupAlreadyExistsException;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupDoesNotExistException;
import java.time.Instant;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException exception) {
    log.warn("Operation failed - Bad Request: {}", exception.getMessage());
    ErrorResponse response =
        new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", exception.getMessage(), Instant.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(MuscleGroupDoesNotExistException.class)
  public ResponseEntity<ErrorResponse> handleMuscleGroupDoesNotExists(
      MuscleGroupDoesNotExistException exception) {
    log.warn("Operation failed - Not exists: {}", exception.getMessage());
    ErrorResponse response =
        new ErrorResponse(
            HttpStatus.NOT_FOUND.value(), "NOT_FOUND", exception.getMessage(), Instant.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(MuscleGroupAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleMuscleGroupAlreadyExists(
      MuscleGroupAlreadyExistsException exception) {
    log.warn("Operation failed - Conflict: {}", exception.getMessage());
    ErrorResponse response =
        new ErrorResponse(
            HttpStatus.CONFLICT.value(), "CONFLICT", exception.getMessage(), Instant.now());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception) {
    log.warn("Operation failed - Bad request: {}", exception.getMessage());
    String message =
        exception.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
    ErrorResponse response =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", message, Instant.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception exception) {
    log.error("Unexpected error.", exception);
    ErrorResponse response =
        new ErrorResponse(
            500, "INTERNAL_SERVER_ERROR", "An unexpected error occurred.", Instant.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
