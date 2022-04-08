package ru.weathertelegrambot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.weathertelegrambot.cache.UserDataCache;
import ru.weathertelegrambot.clients.WeatherClientImpl;
import ru.weathertelegrambot.model.UserProfileData;
import ru.weathertelegrambot.model.WeatherInfo;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
  private final ReplyMessagesService messagesService;
  private final UserDataCache userDataCache;
  private final WeatherClientImpl weatherClient;

  public String getWeatherInfo(Long userId){
    UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
/*    String orderCityName = message.getText().substring(0, 1).toUpperCase() + message.getText().substring(1).toLowerCase();
    userProfileData.setCityName(orderCityName);*/
    WeatherInfo weatherInfo = weatherClient.getWeatherInfo(userProfileData.getType().toString(),
        userProfileData.getCityName());
    if (weatherInfo.getTemperature() == 0 &&
        weatherInfo.getFeelsLike() == 0 &&
        weatherInfo.getWindSpeed() == 0) {

      throw new  WeatherNotFoundException("Not found city name");
    }
    return messagesService.getReplyText("showWeatherInfo",
        userProfileData.getCityName(),
        weatherInfo.getTemperature(),
        weatherInfo.getFeelsLike(),
        weatherInfo.getWindSpeed());
  }

}
