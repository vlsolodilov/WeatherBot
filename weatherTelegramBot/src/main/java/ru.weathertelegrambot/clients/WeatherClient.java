package ru.weathertelegrambot.clients;

import ru.weathertelegrambot.model.WeatherInfo;

public interface WeatherClient {
    WeatherInfo getWeatherInfo(String weatherType, String city);
}
