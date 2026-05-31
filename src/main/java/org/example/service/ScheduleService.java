package org.example.service;

import java.util.List;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;

public interface ScheduleService {

    ScheduleResponseDto create(ScheduleRequestDto requestDto);

    ScheduleResponseDto getById(Long id);

    List<ScheduleResponseDto> getAll();

    ScheduleResponseDto update(Long id, ScheduleRequestDto requestDto);

    void delete(Long id);

    List<ScheduleResponseDto> getByGroupId(Long groupId);

    List<ScheduleResponseDto> getByTeacherId(Long teacherId);
}
