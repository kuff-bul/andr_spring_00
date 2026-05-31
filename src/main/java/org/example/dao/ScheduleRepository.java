package org.example.dao;

import java.util.List;
import org.example.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByGroupId(Long groupId);

    List<Schedule> findByTeacherId(Long teacherId);
}
