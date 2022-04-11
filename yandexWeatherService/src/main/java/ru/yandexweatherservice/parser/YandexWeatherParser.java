package ru.yandexweatherservice.parser;

import ru.yandexweatherservice.model.YandexWeatherInfo;

public interface YandexWeatherParser {
    YandexWeatherInfo parse(String weatherAsString);
}
