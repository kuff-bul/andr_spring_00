package org.example.exception;

public class StudentNotFoundException extends NotFoundException {

    public StudentNotFoundException(Long id) {
        super("Student not found: " + id);
    }
}
