package ru.openweatherservice.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.openweatherservice.model.OpenweatherInfo;
import ru.openweatherservice.requester.RequesterException;
import ru.openweatherservice.services.OpenweatherService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class OpenweatherController {

  public final OpenweatherService openweatherService;

  @GetMapping("/weatherInfo/{cityName}")
  public OpenweatherInfo getOpenweatherInfo(@PathVariable("cityName") String cityName)  {
    log.info("getCurrencyRate, cityName:{}", cityName);

    OpenweatherInfo openweatherInfo = openweatherService.getOpenweatherInfo(cityName);
    log.info("openweatherInfo:{}", openweatherInfo);
    return openweatherInfo;
  }
}
