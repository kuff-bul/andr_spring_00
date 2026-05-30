package org.example.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.GroupRepository;
import org.example.dao.StudentRepository;
import org.example.dto.StudentRequestDto;
import org.example.dto.StudentResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.StudentMapper;
import org.example.model.Group;
import org.example.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentResponseDto create(StudentRequestDto requestDto) {
        Student student = studentMapper.toEntity(requestDto);
        student.setGroup(resolveGroup(requestDto.getGroupId()));
        return studentMapper.toResponseDto(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public StudentResponseDto getById(Long id) {
        return studentMapper.toResponseDto(findById(id));
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public StudentResponseDto update(Long id, StudentRequestDto requestDto) {
        Student student = findById(id);
        studentMapper.updateEntity(requestDto, student);
        student.setGroup(resolveGroup(requestDto.getGroupId()));
        return studentMapper.toResponseDto(studentRepository.save(student));
    }

    @Transactional
    public void delete(Long id) {
        Student student = findById(id);
        studentRepository.delete(student);
    }

    @Transactional
    public StudentResponseDto assignGroup(Long studentId, Long groupId) {
        Student student = findById(studentId);
        Group group = findGroupById(groupId);

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
                .orElseThrow(() -> new NotFoundException("Group not found: " + groupId));
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));
    }
}
