package org.example.dto;

public record StudentResponseDto(
        Long id,
        String firstName,
        String lastName,
        Long groupId,
        String groupName
) {
}
