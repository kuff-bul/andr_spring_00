package org.example.mapper;

import org.example.dto.StudentRequestDto;
import org.example.dto.StudentResponseDto;
import org.example.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    Student toEntity(StudentRequestDto requestDto);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    StudentResponseDto toResponseDto(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    void updateEntity(StudentRequestDto requestDto, @MappingTarget Student student);
}
