package org.example.service;

import java.util.List;
import org.example.dto.GroupRequestDto;
import org.example.dto.GroupResponseDto;
import org.example.model.Group;

public interface GroupService {

    GroupResponseDto create(GroupRequestDto requestDto);

    GroupResponseDto getById(Long id);

    List<GroupResponseDto> getAll();

    GroupResponseDto update(Long id, GroupRequestDto requestDto);

    void delete(Long id);

    Group findById(Long id);
}
