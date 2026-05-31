package org.example.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.GroupRepository;
import org.example.dto.GroupRequestDto;
import org.example.dto.GroupResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.GroupMapper;
import org.example.model.Group;
import org.example.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    @Transactional
    public GroupResponseDto create(GroupRequestDto requestDto) {
        var group = groupMapper.toEntity(requestDto);
        return groupMapper.toResponseDto(groupRepository.save(group));
    }

    @Override
    @Transactional(readOnly = true)
    public GroupResponseDto getById(Long id) {
        return groupMapper.toResponseDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponseDto> getAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public GroupResponseDto update(Long id, GroupRequestDto requestDto) {
        var group = findById(id);
        groupMapper.updateEntity(requestDto, group);
        return groupMapper.toResponseDto(groupRepository.save(group));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var group = findById(id);
        groupRepository.delete(group);
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Group not found: " + id));
    }
}
