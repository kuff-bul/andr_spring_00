package org.example.mapper;

import org.example.dto.TeacherRequestDto;
import org.example.dto.TeacherResponseDto;
import org.example.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", ignore = true)
    Teacher toEntity(TeacherRequestDto requestDto);

    TeacherResponseDto toResponseDto(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    void updateEntity(TeacherRequestDto requestDto, @MappingTarget Teacher teacher);
}
