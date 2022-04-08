package ru.weathertelegrambot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.weathertelegrambot.botapi.TelegramFacade;
import ru.weathertelegrambot.config.BotConfig;

@Component
@RequiredArgsConstructor
public class WeatherTelegramBot extends TelegramLongPollingBot {

  private final BotConfig botConfig;
  private final TelegramFacade telegramFacade;

  @Override
  public String getBotUsername() {
    return botConfig.getName();
  }

  @Override
  public String getBotToken() {
    return botConfig.getToken();
  }

  @Override
  public void onUpdateReceived(Update update) {
    executeMessage(telegramFacade.handleUpdate(update));
  }

  private <T extends BotApiMethod> void executeMessage(T sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
