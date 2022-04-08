package ru.weathertelegrambot.botapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.weathertelegrambot.cache.UserDataCache;
import ru.weathertelegrambot.model.UserProfileData;
import ru.weathertelegrambot.model.WeatherType;
import ru.weathertelegrambot.services.MainMenuService;
import ru.weathertelegrambot.services.ReplyMessagesService;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramFacade {
  private final BotStateContext botStateContext;
  private final UserDataCache userDataCache;
  private final MainMenuService mainMenuService;
  private final ReplyMessagesService messagesService;

  public BotApiMethod<?> handleUpdate(Update update) {
    SendMessage replyMessage = null;

    if (update.hasCallbackQuery()) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
          callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
      return processCallbackQuery(callbackQuery);
    }

    Message message = update.getMessage();
    if (message != null && message.hasText()) {
      log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
          message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
      replyMessage = handleInputMessage(message);
    }

    return replyMessage;
  }

  private SendMessage handleInputMessage(Message message) {
    String inputMsg = message.getText();
    Long userId = message.getFrom().getId();
    BotState botState;
    SendMessage replyMessage;

    switch (inputMsg) {
      case "/start":
      case "Новые настройки":
        botState = BotState.ENTER_WEATHER_TYPE;
        break;
      case "Обновить прогноз":
        botState = BotState.SHOW_WEATHER_INFO;
        break;
      case "Мои настройки":
        botState = BotState.SHOW_USER_DATA;
        break;
      case "Помощь":
        botState = BotState.SHOW_HELP_MENU;
        break;
      default:
        botState = userDataCache.getUsersCurrentBotState(userId);
        break;
    }
    userDataCache.setUsersCurrentBotState(userId, botState);
    replyMessage = botStateContext.processInputMessage(botState, message);

    return replyMessage;
  }

  private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
    final String chatId = String.valueOf(buttonQuery.getMessage().getChatId());
    final Long userId = buttonQuery.getFrom().getId();
    BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");

    if (buttonQuery.getData().equals("buttonOpenWeather")) {
      UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
      userProfileData.setType(WeatherType.OPEN_WEATHER);
      userDataCache.saveUserProfileData(userId, userProfileData);
      userDataCache.setUsersCurrentBotState(userId, BotState.END_ENTER_USER_DATA);
      callBackAnswer = messagesService.getReplyMessage(chatId, "enterCityName");
    } else if (buttonQuery.getData().equals("buttonYandex")) {
      callBackAnswer = sendAnswerCallbackQuery("Данный сервис пока не поддерживается", false, buttonQuery);
    } else {
      userDataCache.setUsersCurrentBotState(userId, BotState.ENTER_WEATHER_TYPE);
    }

    return callBackAnswer;
  }

  private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
    answerCallbackQuery.setShowAlert(alert);
    answerCallbackQuery.setText(text);
    return answerCallbackQuery;
  }
}