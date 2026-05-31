package org.example.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.TeacherRepository;
import org.example.dto.TeacherRequestDto;
import org.example.dto.TeacherResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.TeacherMapper;
import org.example.model.Teacher;
import org.example.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    @Transactional
    public TeacherResponseDto create(TeacherRequestDto requestDto) {
        var teacher = teacherMapper.toEntity(requestDto);
        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDto getById(Long id) {
        return teacherMapper.toResponseDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponseDto> getAll() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public TeacherResponseDto update(Long id, TeacherRequestDto requestDto) {
        var teacher = findById(id);
        teacherMapper.updateEntity(requestDto, teacher);
        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var teacher = findById(id);
        teacherRepository.delete(teacher);
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));
    }
}
