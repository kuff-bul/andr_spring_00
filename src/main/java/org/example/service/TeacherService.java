package org.example.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.TeacherRepository;
import org.example.dto.TeacherRequestDto;
import org.example.dto.TeacherResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.TeacherMapper;
import org.example.model.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Transactional
    public TeacherResponseDto create(TeacherRequestDto requestDto) {
        Teacher teacher = teacherMapper.toEntity(requestDto);
        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Transactional(readOnly = true)
    public TeacherResponseDto getById(Long id) {
        return teacherMapper.toResponseDto(findById(id));
    }

    @Transactional(readOnly = true)
    public List<TeacherResponseDto> getAll() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public TeacherResponseDto update(Long id, TeacherRequestDto requestDto) {
        Teacher teacher = findById(id);
        teacherMapper.updateEntity(requestDto, teacher);
        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Transactional
    public void delete(Long id) {
        Teacher teacher = findById(id);
        teacherRepository.delete(teacher);
    }

    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));
    }
}
