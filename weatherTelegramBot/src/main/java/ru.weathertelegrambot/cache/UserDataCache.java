package ru.weathertelegrambot.cache;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import ru.weathertelegrambot.botapi.BotState;
import ru.weathertelegrambot.model.UserProfileData;

@Component
public class UserDataCache implements DataCache {
  private Map<Long, BotState> usersBotStates = new HashMap<>();
  private Map<Long, UserProfileData> usersProfileData = new HashMap<>();

  @Override
  public void setUsersCurrentBotState(Long userId, BotState botState) {
    usersBotStates.put(userId, botState);
  }

  @Override
  public BotState getUsersCurrentBotState(Long userId) {
    BotState botState = usersBotStates.get(userId);
    if (botState == null) {
      botState = BotState.ENTER_WEATHER_TYPE;
    }
    return botState;
  }

  @Override
  public UserProfileData getUserProfileData(Long userId) {
    UserProfileData userProfileData = usersProfileData.get(userId);
    if (userProfileData == null) {
      userProfileData = new UserProfileData();
    }
    return userProfileData;
  }

  @Override
  public void saveUserProfileData(Long userId, UserProfileData userProfileData) {
    usersProfileData.put(userId, userProfileData);
  }
}