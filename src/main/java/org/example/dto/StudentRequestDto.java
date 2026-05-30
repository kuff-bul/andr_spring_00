package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequestDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Long groupId;
}
