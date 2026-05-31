package org.example.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.GroupRepository;
import org.example.dao.StudentRepository;
import org.example.dto.StudentRequestDto;
import org.example.dto.StudentResponseDto;
import org.example.exception.GroupNotFoundException;
import org.example.exception.StudentNotFoundException;
import org.example.mapper.StudentMapper;
import org.example.model.Group;
import org.example.model.Student;
import org.example.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentResponseDto create(StudentRequestDto requestDto) {
        var student = studentMapper.toEntity(requestDto);
        student.setGroup(resolveGroup(requestDto.getGroupId()));
        return studentMapper.toResponseDto(studentRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getById(Long id) {
        return studentMapper.toResponseDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public StudentResponseDto update(Long id, StudentRequestDto requestDto) {
        var student = findById(id);
        studentMapper.updateEntity(requestDto, student);
        student.setGroup(resolveGroup(requestDto.getGroupId()));
        return studentMapper.toResponseDto(studentRepository.save(student));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var student = findById(id);
        studentRepository.delete(student);
    }

    @Override
    @Transactional
    public StudentResponseDto assignGroup(Long studentId, Long groupId) {
        var student = findById(studentId);
        var group = findGroupById(groupId);

        student.setGroup(group);
        return studentMapper.toResponseDto(studentRepository.save(student));
    }

    private Group resolveGroup(Long groupId) {
        if (groupId == null) {
            return null;
        }
        return findGroupById(groupId);
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    private Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
}
