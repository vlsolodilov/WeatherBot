package ru.yandexweatherservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yandexweather")
public class YandexWeatherConfig {
    String url;
    String headerKey;
    String headerValue;
}
