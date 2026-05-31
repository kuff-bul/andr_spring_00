package org.example.exception;

public class ScheduleNotFoundException extends NotFoundException {

    public ScheduleNotFoundException(Long id) {
        super("Schedule not found: " + id);
    }
}
