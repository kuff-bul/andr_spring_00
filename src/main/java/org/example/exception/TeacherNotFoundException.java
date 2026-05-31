package org.example.exception;

public class TeacherNotFoundException extends NotFoundException {

    public TeacherNotFoundException(Long id) {
        super("Teacher not found: " + id);
    }
}
