package ru.weathertelegrambot.model;

public enum WeatherType {
    OPEN_WEATHER("OpenWeather"),
    YANDEX("Yandex");

    String serviceName;

    WeatherType(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
