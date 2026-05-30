package org.example.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    @NotNull
    private Long groupId;

    @NotNull
    private Long teacherId;

    @NotNull
    private Long courseId;

    @NotNull
    private LocalDateTime lessonDate;
}
