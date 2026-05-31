package org.example.dto;

import jakarta.validation.constraints.NotBlank;

public record GroupRequestDto(
        @NotBlank String name
) {
}
