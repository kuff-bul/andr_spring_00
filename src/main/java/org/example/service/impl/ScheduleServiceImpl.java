package org.example.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.ScheduleRepository;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.ScheduleMapper;
import org.example.model.Schedule;
import org.example.service.CourseService;
import org.example.service.GroupService;
import org.example.service.ScheduleService;
import org.example.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public ScheduleResponseDto create(ScheduleRequestDto requestDto) {
        var schedule = scheduleMapper.toEntity(requestDto);
        applyRelations(schedule, requestDto);
        return scheduleMapper.toResponseDto(scheduleRepository.save(schedule));
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDto getById(Long id) {
        return scheduleMapper.toResponseDto(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAll() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleRequestDto requestDto) {
        var schedule = findById(id);
        scheduleMapper.updateEntity(requestDto, schedule);
        applyRelations(schedule, requestDto);
        return scheduleMapper.toResponseDto(scheduleRepository.save(schedule));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var schedule = findById(id);
        scheduleRepository.delete(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getByGroupId(Long groupId) {
        groupService.findById(groupId);
        return scheduleRepository.findByGroupId(groupId).stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getByTeacherId(Long teacherId) {
        teacherService.findById(teacherId);
        return scheduleRepository.findByTeacherId(teacherId).stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }

    private Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found: " + id));
    }

    private void applyRelations(Schedule schedule, ScheduleRequestDto requestDto) {
        var group = groupService.findById(requestDto.getGroupId());
        var teacher = teacherService.findById(requestDto.getTeacherId());
        var course = courseService.findById(requestDto.getCourseId());

        schedule.setGroup(group);
        schedule.setTeacher(teacher);
        schedule.setCourse(course);
    }
}
