package org.example.service;

import java.util.List;
import org.example.dto.CourseRequestDto;
import org.example.dto.CourseResponseDto;
import org.example.model.Course;

public interface CourseService {

    CourseResponseDto create(CourseRequestDto requestDto);

    CourseResponseDto getById(Long id);

    List<CourseResponseDto> getAll();

    CourseResponseDto update(Long id, CourseRequestDto requestDto);

    void delete(Long id);

    CourseResponseDto assignTeacher(Long courseId, Long teacherId);

    Course findById(Long id);
}
