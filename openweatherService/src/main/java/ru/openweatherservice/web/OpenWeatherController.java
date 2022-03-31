package ru.openweatherservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.openweatherservice.model.OpenWeatherInfo;
import ru.openweatherservice.services.OpenWeatherService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class OpenWeatherController {

  public final OpenWeatherService openweatherService;

  @GetMapping("/weatherInfo/{cityName}")
  public OpenWeatherInfo getOpenWeatherInfo(@PathVariable("cityName") String cityName)  {
    log.info("getOpenWeatherInfo, cityName:{}", cityName);

    OpenWeatherInfo openweatherInfo = openweatherService.getOpenWeatherInfo(cityName);
    log.info("openWeatherInfo:{}", openweatherInfo);
    return openweatherInfo;
  }
}
