package com.example.tskmngr.exceptionHandler;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Пользователь с именем " + username + " уже существует!");
    }
}