package org.example.mapper;

import org.example.dto.GroupRequestDto;
import org.example.dto.GroupResponseDto;
import org.example.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    Group toEntity(GroupRequestDto requestDto);

    GroupResponseDto toResponseDto(Group group);

    @Mapping(target = "id", ignore = true)
    void updateEntity(GroupRequestDto requestDto, @MappingTarget Group group);
}
