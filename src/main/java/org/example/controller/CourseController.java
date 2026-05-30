package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dto.CourseRequestDto;
import org.example.dto.CourseResponseDto;
import org.example.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Course CRUD operations and teacher assignment")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(summary = "Create course", description = "Creates a course and optionally assigns a teacher by teacherId.")
    public ResponseEntity<CourseResponseDto> create(@Valid @RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.status(201).body(courseService.create(requestDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by id", description = "Returns a course by id.")
    public ResponseEntity<CourseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Returns all courses.")
    public ResponseEntity<List<CourseResponseDto>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Updates course fields and optional teacher assignment.")
    public ResponseEntity<CourseResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDto requestDto
    ) {
        return ResponseEntity.ok(courseService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Deletes a course by id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{courseId}/teacher/{teacherId}")
    @Operation(summary = "Assign teacher to course", description = "Assigns or replaces the course's single teacher.")
    public ResponseEntity<CourseResponseDto> assignTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.assignTeacher(courseId, teacherId));
    }
}
