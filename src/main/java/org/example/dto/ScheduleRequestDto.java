package org.example.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ScheduleRequestDto(
        @NotNull Long groupId,
        @NotNull Long teacherId,
        @NotNull Long courseId,
        @NotNull LocalDateTime lessonDate
) {
}
