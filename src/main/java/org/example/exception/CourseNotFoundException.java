package org.example.exception;

public class CourseNotFoundException extends NotFoundException {

    public CourseNotFoundException(Long id) {
        super("Course not found: " + id);
    }
}
