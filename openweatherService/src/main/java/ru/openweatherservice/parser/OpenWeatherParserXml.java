package ru.openweatherservice.parser;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.openweatherservice.model.OpenWeatherInfo;

@Service
@Slf4j
public class OpenWeatherParserXml implements OpenWeatherParser {

  @Override
  public OpenWeatherInfo parse(String weatherAsString) {
    JSONObject obj = new JSONObject(weatherAsString);
    return new OpenWeatherInfo(
        obj.getJSONObject("main").getDouble("temp"),
        obj.getJSONObject("main").getDouble("feels_like"),
        obj.getJSONObject("wind").getDouble("speed"));
  }
}
