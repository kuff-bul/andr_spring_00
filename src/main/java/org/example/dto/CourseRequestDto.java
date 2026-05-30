package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequestDto {

    @NotBlank
    private String name;

    private String description;

    private Long teacherId;
}
