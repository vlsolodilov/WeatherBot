package ru.weathertelegrambot.botapi;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.weathertelegrambot.cache.UserDataCache;
import ru.weathertelegrambot.model.UserProfileData;
import ru.weathertelegrambot.services.MainMenuService;
import ru.weathertelegrambot.services.ReplyMessagesService;
import ru.weathertelegrambot.services.WeatherNotFoundException;
import ru.weathertelegrambot.services.WeatherService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataHandler implements InputMessageHandler {
  private final UserDataCache userDataCache;
  private final ReplyMessagesService messagesService;
  private final WeatherService weatherService;
  private final MainMenuService mainMenuService;

  @Override
  public SendMessage handle(Message message) {
    return processUsersInput(message);
  }

  @Override
  public BotState getHandlerName() {
    return BotState.ENTER_USER_DATA;
  }

  private SendMessage processUsersInput(Message inputMsg) {
    String usersAnswer = inputMsg.getText();
    Long userId = inputMsg.getFrom().getId();
    String chatId = inputMsg.getChatId().toString();

    UserProfileData profileData = userDataCache.getUserProfileData(userId);
    BotState botState = userDataCache.getUsersCurrentBotState(userId);
    log.info("processUsersInput  botState: {}", botState);

    SendMessage replyToUser = null;

    if (botState.equals(BotState.ENTER_WEATHER_TYPE)) {
      replyToUser = messagesService.getReplyMessage(chatId, "enterWeatherType");
      replyToUser.setReplyMarkup(getWeatherTypeButtonsMarkup());
    } else if (botState.equals(BotState.END_ENTER_USER_DATA)) {
      profileData.setCityName(usersAnswer);
      try {
        replyToUser = mainMenuService.getMainMenuMessage(chatId, weatherService.getWeatherInfo(userId));
        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_WEATHER_INFO);
      } catch (WeatherNotFoundException ex) {
        userDataCache.setUsersCurrentBotState(userId, BotState.END_ENTER_USER_DATA);
        replyToUser = messagesService.getReplyMessage(chatId, "notFoundCity");
      }
    }

    userDataCache.saveUserProfileData(userId, profileData);

    return replyToUser;
  }

  private InlineKeyboardMarkup getWeatherTypeButtonsMarkup() {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

    InlineKeyboardButton buttonOpenWeather = new InlineKeyboardButton();
    InlineKeyboardButton buttonYandex = new InlineKeyboardButton();

    buttonOpenWeather.setText("OpenWeather");
    buttonYandex.setText("Yandex");

    buttonOpenWeather.setCallbackData("buttonOpenWeather");
    buttonYandex.setCallbackData("buttonYandex");

    List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
    keyboardButtonsRow1.add(buttonOpenWeather);
    keyboardButtonsRow1.add(buttonYandex);

    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    rowList.add(keyboardButtonsRow1);

    inlineKeyboardMarkup.setKeyboard(rowList);

    return inlineKeyboardMarkup;
  }
}