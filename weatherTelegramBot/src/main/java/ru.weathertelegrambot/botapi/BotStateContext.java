package ru.weathertelegrambot.botapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class BotStateContext {
  private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

  public BotStateContext(List<InputMessageHandler> messageHandlers) {
    messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
  }

  public SendMessage processInputMessage(BotState currentState, Message message) {
    InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
    return currentMessageHandler.handle(message);
  }

  private InputMessageHandler findMessageHandler(BotState currentState) {
    log.info("findMessageHandler botState: {}", currentState);
    if (isEnterUserData(currentState)) {
      return messageHandlers.get(BotState.ENTER_USER_DATA);
    }
    return messageHandlers.get(currentState);
  }

  private boolean isEnterUserData(BotState currentState) {
    log.info("isEnterUserData botState: {}", currentState);
    switch (currentState) {
      case ENTER_USER_DATA:
      case ENTER_WEATHER_TYPE:
      case ENTER_CITY_NAME:
      case END_ENTER_USER_DATA:
        return true;
      default:
        return false;
    }
  }
}