package ru.weatherclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WeatherInfoClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(WeatherInfoClient.class).run(args);
    }
}
