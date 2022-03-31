package ru.openweatherservice.services;

import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import ru.openweatherservice.config.OpenweatherConfig;
import ru.openweatherservice.model.OpenweatherInfo;
import ru.openweatherservice.parser.OpenweatherParser;
import ru.openweatherservice.requester.OpenweatherRequester;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenweatherService {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final OpenweatherRequester openweatherRequester;
    private final OpenweatherParser openweatherParser;
    private final OpenweatherConfig openweatherConfig;
    private final Cache<String, OpenweatherInfo> openweatherInfoCache;

    public OpenweatherInfo getOpenweatherInfo(String cityName) {
        log.info("getCurrencyRate. cityName:{}", cityName);
        OpenweatherInfo openweatherInfo;

        OpenweatherInfo cachedOpenweatherInfo =  openweatherInfoCache.get(cityName);
        if (cachedOpenweatherInfo == null) {
            String urlWithParams = String.format(openweatherConfig.getUrl(), cityName);
            String ratesAsXml = openweatherRequester.getRatesAsXml(urlWithParams);
            openweatherInfo = openweatherParser.parse(ratesAsXml);
            openweatherInfoCache.put(cityName, openweatherInfo);
        } else {
            openweatherInfo = cachedOpenweatherInfo;
        }

        return openweatherInfo;
    }
}
