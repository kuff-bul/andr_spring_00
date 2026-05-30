package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDto {

    private Long id;
    private String name;
    private String description;
    private Long teacherId;
    private String teacherFirstName;
    private String teacherLastName;
}
