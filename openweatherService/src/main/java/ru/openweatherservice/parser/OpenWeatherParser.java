package ru.openweatherservice.parser;

import ru.openweatherservice.model.OpenWeatherInfo;

public interface OpenWeatherParser {
    OpenWeatherInfo parse(String weatherAsString);
}
