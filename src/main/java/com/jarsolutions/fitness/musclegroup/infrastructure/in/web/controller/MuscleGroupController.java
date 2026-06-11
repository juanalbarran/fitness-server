package com.jarsolutions.fitness.musclegroup.infrastructure.in.web.controller;

import com.jarsolutions.fitness.musclegroup.application.port.in.CreateMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.DeleteMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.GetMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.UpdateMuscleGroupUseCase;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/muscle-groups")
public class MuscleGroupController {
  private final CreateMuscleGroupUseCase createUseCase;
  private final UpdateMuscleGroupUseCase updateUseCase;
  private final GetMuscleGroupUseCase getUseCase;
  private final DeleteMuscleGroupUseCase deleteUseCase;
  private final MuscleGroupWebMapper mapper;

  public MuscleGroupController(
      CreateMuscleGroupUseCase createUseCase,
      UpdateMuscleGroupUseCase updateUseCase,
      GetMuscleGroupUseCase getUseCase,
      DeleteMuscleGroupUseCase deleteUseCase,
      MuscleGroupWebMapper mapper) {
    this.mapper = mapper;
    this.createUseCase = createUseCase;
    this.updateUseCase = updateUseCase;
    this.deleteUseCase = deleteUseCase;
    this.getUseCase = getUseCase;
  }

  @PostMapping
  public ResponseEntity<MuscleGroupResponse> create(
      @RequestBody @Valid CreateMuscleGroupRequest request) {
    MuscleGroup created =
        createUseCase.createMuscleGroup(mapper.toCreateMuscleGroupCommand(request));
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
    return ResponseEntity.created(location).body(mapper.toMuscleGroupResponse(created));
  }

  @GetMapping
  public ResponseEntity<List<MuscleGroupResponse>> getAll(
      @RequestParam(required = false) String name) {
    if (name != null) {
      return ResponseEntity.ok(
          getUseCase
              .getMuscleGroupByName(name)
              .map(mapper::toMuscleGroupResponse)
              .map(List::of)
              .orElse(List.of()));
    }
    return ResponseEntity.ok(
        getUseCase.getAllMuscleGroups().stream().map(mapper::toMuscleGroupResponse).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MuscleGroupResponse> getById(@PathVariable Long id) {
    return getUseCase
        .getMuscleGroup(id)
        .map(mapper::toMuscleGroupResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<MuscleGroupResponse> update(
      @PathVariable Long id, @RequestBody @Valid UpdateMuscleGroupRequest request) {
    MuscleGroup updated =
        updateUseCase.updateMuscleGroup(id, mapper.toUpdateMuscleGroupCommand(request));
    return ResponseEntity.ok(mapper.toMuscleGroupResponse(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    deleteUseCase.deleteMuscleGroup(id);
    return ResponseEntity.noContent().build();
  }
}
