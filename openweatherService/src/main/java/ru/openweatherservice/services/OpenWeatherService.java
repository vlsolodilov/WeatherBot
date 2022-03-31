package ru.openweatherservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import ru.openweatherservice.config.OpenWeatherConfig;
import ru.openweatherservice.model.OpenWeatherInfo;
import ru.openweatherservice.parser.OpenWeatherParser;
import ru.openweatherservice.requester.OpenWeatherRequester;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenWeatherService {

    private final OpenWeatherRequester openWeatherRequester;
    private final OpenWeatherParser openWeatherParser;
    private final OpenWeatherConfig openWeatherConfig;
    private final Cache<String, OpenWeatherInfo> openWeatherInfoCache;

    public OpenWeatherInfo getOpenWeatherInfo(String cityName) {
        log.info("getOpenWeatherInfo. cityName:{}", cityName);
        OpenWeatherInfo openweatherInfo;

        OpenWeatherInfo cachedOpenWeatherInfo =  openWeatherInfoCache.get(cityName);
        if (cachedOpenWeatherInfo == null) {
            String urlWithParams = String.format(openWeatherConfig.getUrl(), cityName);
            String weatherAsXml = openWeatherRequester.getWeatherAsXml(urlWithParams);
            openweatherInfo = openWeatherParser.parse(weatherAsXml);
            openWeatherInfoCache.put(cityName, openweatherInfo);
        } else {
            openweatherInfo = cachedOpenWeatherInfo;
        }

        return openweatherInfo;
    }
}
