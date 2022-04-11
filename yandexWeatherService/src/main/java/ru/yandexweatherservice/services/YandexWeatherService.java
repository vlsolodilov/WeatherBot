package ru.yandexweatherservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import ru.yandexweatherservice.config.YandexWeatherConfig;
import ru.yandexweatherservice.model.YandexWeatherInfo;
import ru.yandexweatherservice.parser.YandexWeatherParser;
import ru.yandexweatherservice.requester.YandexWeatherRequester;

@Service
@Slf4j
@RequiredArgsConstructor
public class YandexWeatherService {

    private final YandexWeatherRequester yandexWeatherRequester;
    private final YandexWeatherParser yandexWeatherParser;
    private final YandexWeatherConfig yandexWeatherConfig;
    private final Cache<String, YandexWeatherInfo> yandexWeatherInfoCache;

    public YandexWeatherInfo getYandexWeatherInfo(String lat, String lon) {
        log.info("getYandexWeatherInfo. lat:{}, lon:{}", lat, lon);
        YandexWeatherInfo yandexWeatherInfo;

        String location = lat + lon;
        YandexWeatherInfo cachedOpenWeatherInfo =  yandexWeatherInfoCache.get(location);
        if (cachedOpenWeatherInfo == null) {
            String urlWithParams = String.format(yandexWeatherConfig.getUrl(), lat, lon);
            String weatherAsXml = yandexWeatherRequester.getWeatherAsXml(urlWithParams);
            yandexWeatherInfo = yandexWeatherParser.parse(weatherAsXml);
            yandexWeatherInfoCache.put(location, yandexWeatherInfo);
        } else {
            yandexWeatherInfo = cachedOpenWeatherInfo;
        }

        return yandexWeatherInfo;
    }
}
