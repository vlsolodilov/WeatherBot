package ru.weatherclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WeatherInfo {
    double temperature;
    double feelsLike;
    double windSpeed;

    @JsonCreator
    public WeatherInfo(@JsonProperty("temperature") double temperature,
                        @JsonProperty("feelsLike") double feelsLike,
                        @JsonProperty("windSpeed") double windSpeed) {
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.windSpeed = windSpeed;
    }
}
