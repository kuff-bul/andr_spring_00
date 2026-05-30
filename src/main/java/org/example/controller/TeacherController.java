package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dto.TeacherRequestDto;
import org.example.dto.TeacherResponseDto;
import org.example.dto.ScheduleResponseDto;
import org.example.service.ScheduleService;
import org.example.service.TeacherService;
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
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Tag(name = "Teachers", description = "Teacher CRUD operations and teacher schedule")
public class TeacherController {

    private final TeacherService teacherService;
    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(summary = "Create teacher", description = "Creates a teacher.")
    public ResponseEntity<TeacherResponseDto> create(@Valid @RequestBody TeacherRequestDto requestDto) {
        return ResponseEntity.status(201).body(teacherService.create(requestDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by id", description = "Returns a teacher by id.")
    public ResponseEntity<TeacherResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all teachers", description = "Returns all teachers.")
    public ResponseEntity<List<TeacherResponseDto>> getAll() {
        return ResponseEntity.ok(teacherService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update teacher", description = "Updates a teacher by id.")
    public ResponseEntity<TeacherResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody TeacherRequestDto requestDto
    ) {
        return ResponseEntity.ok(teacherService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete teacher", description = "Deletes a teacher by id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teacherId}/schedule")
    @Operation(summary = "Get teacher schedule", description = "Returns schedule entries for the selected teacher.")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedule(@PathVariable Long teacherId) {
        return ResponseEntity.ok(scheduleService.getByTeacherId(teacherId));
    }
}
