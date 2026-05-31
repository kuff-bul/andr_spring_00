package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dto.StudentRequestDto;
import org.example.dto.StudentResponseDto;
import org.example.service.StudentService;
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
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Student CRUD operations and group assignment")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Create student", description = "Creates a student and optionally assigns a group by groupId.")
    public ResponseEntity<StudentResponseDto> create(@Valid @RequestBody StudentRequestDto requestDto) {
        return ResponseEntity.status(201).body(studentService.create(requestDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by id", description = "Returns a student by id.")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all students", description = "Returns all students.")
    public ResponseEntity<List<StudentResponseDto>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Updates student fields and optional group assignment.")
    public ResponseEntity<StudentResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto requestDto
    ) {
        return ResponseEntity.ok(studentService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Deletes a student by id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{studentId}/group/{groupId}")
    @Operation(summary = "Assign student to group", description = "Assigns or replaces the student's single group.")
    public ResponseEntity<StudentResponseDto> assignGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.assignGroup(studentId, groupId));
    }
}
