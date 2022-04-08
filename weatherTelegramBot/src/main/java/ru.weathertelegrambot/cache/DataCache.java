package ru.weathertelegrambot.cache;

import ru.weathertelegrambot.botapi.BotState;
import ru.weathertelegrambot.model.UserProfileData;

public interface DataCache {
  void setUsersCurrentBotState(Long userId, BotState botState);

  BotState getUsersCurrentBotState(Long userId);

  UserProfileData getUserProfileData(Long userId);

  void saveUserProfileData(Long userId, UserProfileData userProfileData);
}
