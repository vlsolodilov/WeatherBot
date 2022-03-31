package ru.weatherclient.model;

public enum WeatherType {
    OPEN_WEATHER("openweather"),
    YANDEX("yandex");

    String serviceName;

    WeatherType(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
