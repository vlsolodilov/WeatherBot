package ru.yandexweatherservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandexweatherservice.model.YandexWeatherInfo;
import ru.yandexweatherservice.services.YandexWeatherService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class YandexWeatherController {

  public final YandexWeatherService yandexWeatherService;

  @GetMapping("/weatherInfo")
  public YandexWeatherInfo getYandexWeatherInfo(@RequestParam("lat") String lat, @RequestParam("lon") String lon)  {
    log.info("getYandexWeatherInfo, lat:{}, lon:{}", lat, lon);

    YandexWeatherInfo yandexWeatherInfo = yandexWeatherService.getYandexWeatherInfo(lat, lon);
    log.info("yandexWeatherInfo:{}", yandexWeatherInfo);
    return yandexWeatherInfo;
  }
}
