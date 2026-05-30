package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long groupId;
    private String groupName;
}
