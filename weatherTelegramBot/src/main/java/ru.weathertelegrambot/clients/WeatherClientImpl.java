package ru.weathertelegrambot.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.weathertelegrambot.config.AppConfig;
import ru.weathertelegrambot.model.WeatherInfo;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherClientImpl implements WeatherClient {
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final AppConfig config;


    @Override
    public WeatherInfo getWeatherInfo(String weatherType, String cityName) {
        log.info("getWeatherInfo cityName:{}", cityName);
        String urlWithParams = String.format("%s/%s/%s", config.getUrl(), weatherType, cityName);

        try {
            String response = httpClient.performRequest(urlWithParams);
            return objectMapper.readValue(response, WeatherInfo.class);
        } catch (HttpClientException ex) {
            throw new WeatherClientException("Error from OpenWeather Client host:" + ex.getMessage());
        } catch (Exception ex) {
            log.error("Getting WeatherInfo error, cityName:{}", cityName, ex);
            throw new WeatherClientException("Can't get WeatherInfo. cityName:" + cityName);
        }
    }
}
