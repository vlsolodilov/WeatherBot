package ru.weathertelegrambot.botapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.weathertelegrambot.cache.UserDataCache;
import ru.weathertelegrambot.model.UserProfileData;
import ru.weathertelegrambot.services.ReplyMessagesService;

@Component
@RequiredArgsConstructor
public class ShowProfileHandler implements InputMessageHandler {
  private final UserDataCache userDataCache;
  private final ReplyMessagesService messagesService;

  @Override
  public SendMessage handle(Message message) {
    final Long userId = message.getFrom().getId();
    final UserProfileData profileData = userDataCache.getUserProfileData(userId);
    //userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_WEATHER_INFO);
    return new SendMessage(String.valueOf(message.getChatId()),
        messagesService.getReplyText("showProfile", profileData.getType().getServiceName(), profileData.getCityName()));
  }

  @Override
  public BotState getHandlerName() {
    return BotState.SHOW_USER_DATA;
  }
}
