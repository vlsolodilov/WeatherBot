package ru.openweatherservice.parser;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.openweatherservice.model.OpenweatherInfo;

@Service
@Slf4j
public class OpenweatherParserXml implements OpenweatherParser {

  @Override
  public OpenweatherInfo parse(String weatherAsString) {
    JSONObject obj = new JSONObject(weatherAsString);
    return new OpenweatherInfo(
        obj.getJSONObject("main").getDouble("temp"),
        obj.getJSONObject("main").getDouble("feels_like"),
        obj.getJSONObject("wind").getDouble("speed"));
  }
}
