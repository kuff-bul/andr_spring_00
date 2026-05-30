package org.example.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.GroupRepository;
import org.example.dto.GroupRequestDto;
import org.example.dto.GroupResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.GroupMapper;
import org.example.model.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Transactional
    public GroupResponseDto create(GroupRequestDto requestDto) {
        Group group = groupMapper.toEntity(requestDto);
        return groupMapper.toResponseDto(groupRepository.save(group));
    }

    @Transactional(readOnly = true)
    public GroupResponseDto getById(Long id) {
        return groupMapper.toResponseDto(findById(id));
    }

    @Transactional(readOnly = true)
    public List<GroupResponseDto> getAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public GroupResponseDto update(Long id, GroupRequestDto requestDto) {
        Group group = findById(id);
        groupMapper.updateEntity(requestDto, group);
        return groupMapper.toResponseDto(groupRepository.save(group));
    }

    @Transactional
    public void delete(Long id) {
        Group group = findById(id);
        groupRepository.delete(group);
    }

    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Group not found: " + id));
    }
}
