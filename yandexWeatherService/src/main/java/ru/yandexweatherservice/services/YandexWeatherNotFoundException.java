package ru.yandexweatherservice.services;

public class YandexWeatherNotFoundException extends RuntimeException {
    public YandexWeatherNotFoundException(String message) {
        super(message);
    }
}
