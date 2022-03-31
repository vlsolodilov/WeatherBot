package ru.weatherclient.clients;

import ru.weatherclient.model.WeatherInfo;

public interface WeatherClient {
    WeatherInfo getWeatherInfo(String city);
}
