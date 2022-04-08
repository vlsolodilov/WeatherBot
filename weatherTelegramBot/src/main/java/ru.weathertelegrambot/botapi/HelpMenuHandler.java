package ru.weathertelegrambot.botapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.weathertelegrambot.botapi.BotState;
import ru.weathertelegrambot.botapi.InputMessageHandler;
import ru.weathertelegrambot.cache.UserDataCache;
import ru.weathertelegrambot.model.UserProfileData;
import ru.weathertelegrambot.services.MainMenuService;
import ru.weathertelegrambot.services.ReplyMessagesService;

@Component
@RequiredArgsConstructor
public class HelpMenuHandler implements InputMessageHandler {
  private final UserDataCache userDataCache;
  private final MainMenuService mainMenuService;
  private final ReplyMessagesService messagesService;

  @Override
  public SendMessage handle(Message message) {
    final Long userId = message.getFrom().getId();
    //userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_WEATHER_INFO);
    return mainMenuService.getMainMenuMessage(message.getChatId().toString(),
        messagesService.getReplyText("showHelpMenu"));
  }

  @Override
  public BotState getHandlerName() {
    return BotState.SHOW_HELP_MENU;
  }
}