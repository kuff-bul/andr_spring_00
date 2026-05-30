package org.example.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponseDto {

    private Long id;
    private Long groupId;
    private String groupName;
    private Long teacherId;
    private String teacherFirstName;
    private String teacherLastName;
    private Long courseId;
    private String courseName;
    private LocalDateTime lessonDate;
}
