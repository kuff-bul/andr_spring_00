package org.example.dto;

import jakarta.validation.constraints.NotBlank;

public record TeacherRequestDto(
        @NotBlank String firstName,
        @NotBlank String lastName
) {
}
