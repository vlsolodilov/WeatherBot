package ru.weathertelegrambot.botapi;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.weathertelegrambot.cache.UserDataCache;
import ru.weathertelegrambot.clients.WeatherClientImpl;
import ru.weathertelegrambot.model.UserProfileData;
import ru.weathertelegrambot.model.WeatherInfo;
import ru.weathertelegrambot.services.MainMenuService;
import ru.weathertelegrambot.services.ReplyMessagesService;
import ru.weathertelegrambot.services.WeatherService;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshHandler implements InputMessageHandler {
  private final MainMenuService mainMenuService;
  private final WeatherService weatherService;

  @Override
  public SendMessage handle(Message message) {
    Long userId = message.getFrom().getId();
    return mainMenuService.getMainMenuMessage(message.getChatId().toString(),
        weatherService.getWeatherInfo(userId));
  }

  @Override
  public BotState getHandlerName() {
    return BotState.SHOW_WEATHER_INFO;
  }
}