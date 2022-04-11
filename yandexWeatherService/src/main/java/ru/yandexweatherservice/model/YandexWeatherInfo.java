package ru.yandexweatherservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class YandexWeatherInfo {
    double temperature;
    double feelsLike;
    double windSpeed;
}
