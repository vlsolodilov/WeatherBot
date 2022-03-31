package ru.weatherclient.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.weatherclient.model.WeatherInfo;
import ru.weatherclient.model.WeatherType;
import ru.weatherclient.services.WeatherEndpointService;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class WeatherEndpointController {

    public final WeatherEndpointService currencyRateEndpointService;

    @GetMapping("/weather/{type}/{cityName}")
    public WeatherInfo getWeatherInfo(@PathVariable("type") WeatherType type,
        @PathVariable("cityName") String cityName)  {
        log.info("getWeatherInfo, cityName:{}", cityName);

        WeatherInfo weatherInfo = currencyRateEndpointService.getWeatherInfo(type, cityName);
        log.info("weatherInfo:{}", weatherInfo);
        return weatherInfo;
    }
}