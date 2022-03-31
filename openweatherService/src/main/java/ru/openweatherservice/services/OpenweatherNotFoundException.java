package ru.openweatherservice.services;

public class OpenweatherNotFoundException extends RuntimeException {
    public OpenweatherNotFoundException(String message) {
        super(message);
    }
}
