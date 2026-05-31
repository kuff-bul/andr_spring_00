package org.example.exception;

public class GroupNotFoundException extends NotFoundException {

    public GroupNotFoundException(Long id) {
        super("Group not found: " + id);
    }
}
