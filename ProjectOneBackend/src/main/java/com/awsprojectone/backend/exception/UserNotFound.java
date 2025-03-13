package com.awsprojectone.backend.exception;

public class UserNotFound extends Exception {
    public UserNotFound(String message) {
        super(message);
    }
}
