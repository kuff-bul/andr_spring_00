package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dto.GroupRequestDto;
import org.example.dto.GroupResponseDto;
import org.example.dto.ScheduleResponseDto;
import org.example.service.GroupService;
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
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Groups", description = "Student group CRUD operations and group schedule")
public class GroupController {

    private final GroupService groupService;
    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(summary = "Create group", description = "Creates a student group.")
    public ResponseEntity<GroupResponseDto> create(@Valid @RequestBody GroupRequestDto requestDto) {
        return ResponseEntity.status(201).body(groupService.create(requestDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by id", description = "Returns a student group by id.")
    public ResponseEntity<GroupResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all groups", description = "Returns all student groups.")
    public ResponseEntity<List<GroupResponseDto>> getAll() {
        return ResponseEntity.ok(groupService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update group", description = "Updates a student group by id.")
    public ResponseEntity<GroupResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody GroupRequestDto requestDto
    ) {
        return ResponseEntity.ok(groupService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete group", description = "Deletes a student group by id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/schedule")
    @Operation(summary = "Get group schedule", description = "Returns schedule entries for the selected group.")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedule(@PathVariable Long groupId) {
        return ResponseEntity.ok(scheduleService.getByGroupId(groupId));
    }
}
