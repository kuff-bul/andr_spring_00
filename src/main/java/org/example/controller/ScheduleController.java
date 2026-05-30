package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.service.ScheduleService;
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
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedules", description = "Schedule CRUD operations")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(summary = "Create schedule entry", description = "Creates a lesson schedule entry for a group, teacher, course and lesson date.")
    public ResponseEntity<ScheduleResponseDto> create(@Valid @RequestBody ScheduleRequestDto requestDto) {
        return ResponseEntity.status(201).body(scheduleService.create(requestDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get schedule entry by id", description = "Returns a schedule entry by id.")
    public ResponseEntity<ScheduleResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all schedule entries", description = "Returns all schedule entries.")
    public ResponseEntity<List<ScheduleResponseDto>> getAll() {
        return ResponseEntity.ok(scheduleService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update schedule entry", description = "Updates group, teacher, course and lesson date for a schedule entry.")
    public ResponseEntity<ScheduleResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequestDto requestDto
    ) {
        return ResponseEntity.ok(scheduleService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete schedule entry", description = "Deletes a schedule entry by id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
