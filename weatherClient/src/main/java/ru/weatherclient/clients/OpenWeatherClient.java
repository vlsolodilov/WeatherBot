package ru.weatherclient.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.weatherclient.config.OpenWeatherClientConfig;
import ru.weatherclient.model.WeatherInfo;

@Service("openweather")
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherClient implements WeatherClient {

    private final OpenWeatherClientConfig config;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Override
    public WeatherInfo getWeatherInfo(String cityName) {
        log.info("getWeatherInfo cityName:{}", cityName);
        String urlWithParams = String.format("%s/%s", config.getUrl(), cityName);

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
