package com.crocobet.example.exceptions;

public class DuplicateUserException extends Exception {
    public DuplicateUserException(String username) {
        super(String.format("Username %s is duplicated", username));
    }
}
