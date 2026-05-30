package org.example.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.ScheduleRepository;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.exception.NotFoundException;
import org.example.mapper.ScheduleMapper;
import org.example.model.Course;
import org.example.model.Group;
import org.example.model.Schedule;
import org.example.model.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleResponseDto create(ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleMapper.toEntity(requestDto);
        applyRelations(schedule, requestDto);
        return scheduleMapper.toResponseDto(scheduleRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getById(Long id) {
        return scheduleMapper.toResponseDto(findById(id));
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAll() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = findById(id);
        scheduleMapper.updateEntity(requestDto, schedule);
        applyRelations(schedule, requestDto);
        return scheduleMapper.toResponseDto(scheduleRepository.save(schedule));
    }

    @Transactional
    public void delete(Long id) {
        Schedule schedule = findById(id);
        scheduleRepository.delete(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getByGroupId(Long groupId) {
        groupService.findById(groupId);
        return scheduleRepository.findByGroupId(groupId).stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getByTeacherId(Long teacherId) {
        teacherService.findById(teacherId);
        return scheduleRepository.findByTeacherId(teacherId).stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found: " + id));
    }

    private void applyRelations(Schedule schedule, ScheduleRequestDto requestDto) {
        Group group = groupService.findById(requestDto.getGroupId());
        Teacher teacher = teacherService.findById(requestDto.getTeacherId());
        Course course = courseService.findById(requestDto.getCourseId());

        schedule.setGroup(group);
        schedule.setTeacher(teacher);
        schedule.setCourse(course);
    }
}
