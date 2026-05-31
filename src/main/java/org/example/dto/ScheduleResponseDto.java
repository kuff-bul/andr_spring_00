package org.example.dto;

import java.time.LocalDateTime;

public record ScheduleResponseDto(
        Long id,
        Long groupId,
        String groupName,
        Long teacherId,
        String teacherFirstName,
        String teacherLastName,
        Long courseId,
        String courseName,
        LocalDateTime lessonDate
) {
}
