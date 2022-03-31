package ru.weatherclient.services;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.weatherclient.clients.WeatherClient;
import ru.weatherclient.model.WeatherInfo;
import ru.weatherclient.model.WeatherType;

@Service
@Slf4j
public class WeatherEndpointService {

    private final Map<String, WeatherClient> clients;

    public WeatherEndpointService(Map<String, WeatherClient> clients) {
        this.clients = clients;
    }

    public WeatherInfo getWeatherInfo(WeatherType weatherType, String cityName) {
        log.info("getWeatherInfo. weatherType:{}, cityName:{}", weatherType, cityName);
        WeatherClient client = clients.get(weatherType.getServiceName());
        return client.getWeatherInfo(cityName);
    }
}
