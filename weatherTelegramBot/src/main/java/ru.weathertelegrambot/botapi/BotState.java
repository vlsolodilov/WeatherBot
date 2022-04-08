package ru.weathertelegrambot.botapi;

public enum BotState {
  ENTER_USER_DATA,
  ENTER_WEATHER_TYPE,
  ENTER_CITY_NAME,
  END_ENTER_USER_DATA,
  SHOW_WEATHER_INFO,
  SHOW_USER_DATA,
  NEW_USER_DATA,
  SHOW_HELP_MENU;
}
