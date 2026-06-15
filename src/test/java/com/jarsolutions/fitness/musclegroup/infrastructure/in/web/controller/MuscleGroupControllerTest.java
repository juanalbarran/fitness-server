package com.jarsolutions.fitness.musclegroup.infrastructure.in.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jarsolutions.fitness.common.infrastructure.in.web.exception.GlobalExceptionHandler;
import com.jarsolutions.fitness.musclegroup.application.port.in.CreateMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.DeleteMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.GetMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.application.port.in.UpdateMuscleGroupUseCase;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupAlreadyExistsException;
import com.jarsolutions.fitness.musclegroup.domain.exception.MuscleGroupDoesNotExistException;
import com.jarsolutions.fitness.musclegroup.domain.model.MuscleGroup;
import com.jarsolutions.fitness.musclegroup.infrastructure.in.web.mapper.MuscleGroupWebMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MuscleGroupController.class)
@Import({MuscleGroupWebMapper.class, GlobalExceptionHandler.class})
class MuscleGroupControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CreateMuscleGroupUseCase createUseCase;

  @MockitoBean
  private UpdateMuscleGroupUseCase updateUseCase;

  @MockitoBean
  private GetMuscleGroupUseCase getUseCase;

  @MockitoBean
  private DeleteMuscleGroupUseCase deleteUseCase;

  @Test
  void createMuscleGroup_validRequest_returns201WithLocationAndBody() throws Exception {
    var created = new MuscleGroup(1L, "Chest", List.of());
    when(createUseCase.createMuscleGroup(any())).thenReturn(created);

    mockMvc
        .perform(
            post("/api/v1/muscle-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Chest\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("/api/v1/muscle-groups/1")))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Chest"))
        .andExpect(jsonPath("$.muscles").isArray());
  }

  @Test
  void createMuscleGroup_blankName_returns400WithErrorBody() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/muscle-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("name: must not be blank"));
  }

  @Test
  void getAllMuscleGroups_returns200WithList() throws Exception {
    when(getUseCase.getAllMuscleGroups())
        .thenReturn(
            List.of(
                new MuscleGroup(1L, "Chest", List.of()), new MuscleGroup(2L, "Back", List.of())));

    mockMvc
        .perform(get("/api/v1/muscle-groups"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].name").value("Chest"))
        .andExpect(jsonPath("$[1].name").value("Back"));
  }

  @Test
  void getAllMuscleGroups_withNameParam_whenFound_returns200WithSingletonList() throws Exception {
    when(getUseCase.getMuscleGroupByName("Chest"))
        .thenReturn(Optional.of(new MuscleGroup(1L, "Chest", List.of())));

    mockMvc
        .perform(get("/api/v1/muscle-groups").param("name", "Chest"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].name").value("Chest"));
  }

  @Test
  void getAllMuscleGroups_withNameParam_whenNotFound_returns200WithEmptyList() throws Exception {
    when(getUseCase.getMuscleGroupByName("Unknown")).thenReturn(Optional.empty());

    mockMvc
        .perform(get("/api/v1/muscle-groups").param("name", "Unknown"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void getMuscleGroupById_whenFound_returns200WithBody() throws Exception {
    when(getUseCase.getMuscleGroup(1L))
        .thenReturn(Optional.of(new MuscleGroup(1L, "Chest", List.of())));

    mockMvc
        .perform(get("/api/v1/muscle-groups/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Chest"));
  }

  @Test
  void getMuscleGroupById_whenNotFound_returns404() throws Exception {
    when(getUseCase.getMuscleGroup(999L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/muscle-groups/999")).andExpect(status().isNotFound());
  }

  @Test
  void updateMuscleGroup_validRequest_returns200WithUpdatedBody() throws Exception {
    var updated = new MuscleGroup(1L, "Back", List.of());
    when(updateUseCase.updateMuscleGroup(eq(1L), any())).thenReturn(updated);

    mockMvc
        .perform(
            put("/api/v1/muscle-groups/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Back\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Back"));
  }

  @Test
  void updateMuscleGroup_blankName_returns400WithErrorBody() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/muscle-groups/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("name: must not be blank"));
  }

  @Test
  void deleteMuscleGroup_returns204() throws Exception {
    doNothing().when(deleteUseCase).deleteMuscleGroup(1L);

    mockMvc.perform(delete("/api/v1/muscle-groups/1")).andExpect(status().isNoContent());
  }

  @Test
  void createMuscleGroup_duplicateName_returns409WithErrorBody() throws Exception {
    when(createUseCase.createMuscleGroup(any()))
        .thenThrow(new MuscleGroupAlreadyExistsException("Muscle group 'Chest' already exists"));

    mockMvc
        .perform(
            post("/api/v1/muscle-groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Chest\"}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.status").value(409))
        .andExpect(jsonPath("$.error").value("CONFLICT"))
        .andExpect(jsonPath("$.message").value("Muscle group 'Chest' already exists"));
  }

  @Test
  void updateMuscleGroup_nonExistentId_returns404WithErrorBody() throws Exception {
    when(updateUseCase.updateMuscleGroup(eq(999L), any()))
        .thenThrow(
            new MuscleGroupDoesNotExistException("Muscle group with id 999 does not exist"));

    mockMvc
        .perform(
            put("/api/v1/muscle-groups/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Back\"}"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Muscle group with id 999 does not exist"));
  }

  @Test
  void deleteMuscleGroup_nonExistentId_returns404WithErrorBody() throws Exception {
    doThrow(new MuscleGroupDoesNotExistException("Muscle group with id 999 does not exist"))
        .when(deleteUseCase)
        .deleteMuscleGroup(999L);

    mockMvc
        .perform(delete("/api/v1/muscle-groups/999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Muscle group with id 999 does not exist"));
  }
}
