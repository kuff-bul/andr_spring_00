package org.example.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.CourseRepository;
import org.example.dto.CourseRequestDto;
import org.example.dto.CourseResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.CourseMapper;
import org.example.model.Course;
import org.example.model.Teacher;
import org.example.service.CourseService;
import org.example.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public CourseResponseDto create(CourseRequestDto requestDto) {
        var course = courseMapper.toEntity(requestDto);
        course.setTeacher(resolveTeacher(requestDto.getTeacherId()));
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponseDto getById(Long id) {
        return courseMapper.toResponseDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CourseResponseDto update(Long id, CourseRequestDto requestDto) {
        var course = findById(id);
        courseMapper.updateEntity(requestDto, course);
        course.setTeacher(resolveTeacher(requestDto.getTeacherId()));
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var course = findById(id);
        courseRepository.delete(course);
    }

    @Override
    @Transactional
    public CourseResponseDto assignTeacher(Long courseId, Long teacherId) {
        var course = findById(courseId);
        var teacher = teacherService.findById(teacherId);
        course.setTeacher(teacher);
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));
    }

    private Teacher resolveTeacher(Long teacherId) {
        if (teacherId == null) {
            return null;
        }
        return teacherService.findById(teacherId);
    }
}
