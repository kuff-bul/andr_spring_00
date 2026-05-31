package org.example.dto;

public record CourseResponseDto(
        Long id,
        String name,
        String description,
        Long teacherId,
        String teacherFirstName,
        String teacherLastName
) {
}
