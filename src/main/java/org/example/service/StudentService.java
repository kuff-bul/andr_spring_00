package org.example.service;

import java.util.List;
import org.example.dto.StudentRequestDto;
import org.example.dto.StudentResponseDto;

public interface StudentService {

    StudentResponseDto create(StudentRequestDto requestDto);

    StudentResponseDto getById(Long id);

    List<StudentResponseDto> getAll();

    StudentResponseDto update(Long id, StudentRequestDto requestDto);

    void delete(Long id);

    StudentResponseDto assignGroup(Long studentId, Long groupId);
}
