package ru.weatherclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "openweather-client")
public class OpenWeatherClientConfig {
    String url;
}
