package com.crocobet.example.exception;

public class UserDuplicateException extends Exception {
    public UserDuplicateException(String username) {
        super(String.format("Username %s is duplicated", username));
    }
}
