package org.example.dto;

import jakarta.validation.constraints.NotBlank;

public record StudentRequestDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        Long groupId
) {
}
