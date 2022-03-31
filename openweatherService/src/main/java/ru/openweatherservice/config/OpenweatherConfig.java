package ru.openweatherservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "openweather")
public class OpenweatherConfig {
    String url;
}
