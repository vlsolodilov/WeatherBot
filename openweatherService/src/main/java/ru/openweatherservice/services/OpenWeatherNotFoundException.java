package ru.openweatherservice.services;

public class OpenWeatherNotFoundException extends RuntimeException {
    public OpenWeatherNotFoundException(String message) {
        super(message);
    }
}
