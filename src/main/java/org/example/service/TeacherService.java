package org.example.service;

import java.util.List;
import org.example.dto.TeacherRequestDto;
import org.example.dto.TeacherResponseDto;
import org.example.model.Teacher;

public interface TeacherService {

    TeacherResponseDto create(TeacherRequestDto requestDto);

    TeacherResponseDto getById(Long id);

    List<TeacherResponseDto> getAll();

    TeacherResponseDto update(Long id, TeacherRequestDto requestDto);

    void delete(Long id);

    Teacher findById(Long id);
}
