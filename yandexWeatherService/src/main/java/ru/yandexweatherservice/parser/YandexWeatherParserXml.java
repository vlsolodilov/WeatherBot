package ru.yandexweatherservice.parser;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.yandexweatherservice.model.YandexWeatherInfo;

@Service
@Slf4j
public class YandexWeatherParserXml implements YandexWeatherParser {

  @Override
  public YandexWeatherInfo parse(String weatherAsString) {
    JSONObject obj = new JSONObject(weatherAsString);
    return new YandexWeatherInfo(
        obj.getJSONObject("fact").getDouble("temp"),
        obj.getJSONObject("fact").getDouble("feels_like"),
        obj.getJSONObject("fact").getDouble("wind_speed"));
  }
}
