package org.example.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseRequestDto(
        @NotBlank String name,
        String description,
        Long teacherId
) {
}
