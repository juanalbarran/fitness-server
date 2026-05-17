package com.jarsolutions.fitness.musclegroup.infrastructure.in.web.controller;

import com.jarsolutions.fitness.musclegroup.application.port.in.MuscleGroupUseCases;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.mapper.MuscleGroupWebMapper;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.request.CreateMuscleGroupRequest;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.request.UpdateMuscleGroupRequest;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.response.MuscleGroupResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/muscle-group")
public class MuscleGroupController {
  private final MuscleGroupUseCases useCases;
  private final MuscleGroupWebMapper mapper;

  public MuscleGroupController(MuscleGroupUseCases useCases, MuscleGroupWebMapper mapper) {
    this.mapper = mapper;
    this.useCases = useCases;
  }

  @PostMapping
  public ResponseEntity<MuscleGroupResponse> create(
      @RequestBody @Valid CreateMuscleGroupRequest request) {
    MuscleGroup created = useCases.createMuscleGroup(mapper.toCreateMuscleGroupCommand(request));
    return ResponseEntity.created(URI.create("/api/v1/muscle-group/" + created.getId()))
        .body(mapper.toMuscleGroupResponse(created));
  }

  @GetMapping
  public ResponseEntity<List<MuscleGroupResponse>> getAllMuscleGroups() {
    return ResponseEntity.ok(
        useCases.getAllMuscleGroups().stream().map(mapper::toMuscleGroupResponse).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MuscleGroupResponse> getById(@PathVariable Long id) {
    return useCases
        .getMuscleGroup(id)
        .map(mapper::toMuscleGroupResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<MuscleGroupResponse> update(
      @PathVariable Long id, @RequestBody @Valid UpdateMuscleGroupRequest request) {
    MuscleGroup updated =
        useCases.updateMuscleGroup(id, mapper.toUpdateMuscleGroupCommand(request));
    return ResponseEntity.ok(mapper.toMuscleGroupResponse(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    useCases.deleteMuscleGroup(id);
    return ResponseEntity.noContent().build();
  }
}
