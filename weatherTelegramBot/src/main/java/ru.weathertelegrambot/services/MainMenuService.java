package ru.weathertelegrambot.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
public class MainMenuService {

  public SendMessage getMainMenuMessage(final String chatId, final String textMessage) {
    final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
    final SendMessage mainMenuMessage =
        createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

    return mainMenuMessage;
  }

  private ReplyKeyboardMarkup getMainMenuKeyboard() {

    final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(false);

    List<KeyboardRow> keyboard = new ArrayList<>();

    KeyboardRow row1 = new KeyboardRow();
    KeyboardRow row2 = new KeyboardRow();
    KeyboardRow row3 = new KeyboardRow();
    KeyboardRow row4 = new KeyboardRow();
    row1.add(new KeyboardButton("Обновить прогноз"));
    row2.add(new KeyboardButton("Мои настройки"));
    row3.add(new KeyboardButton("Новые настройки"));
    row4.add(new KeyboardButton("Помощь"));
    keyboard.add(row1);
    keyboard.add(row2);
    keyboard.add(row3);
    keyboard.add(row4);
    replyKeyboardMarkup.setKeyboard(keyboard);
    return replyKeyboardMarkup;
  }

  private SendMessage createMessageWithKeyboard(final String chatId,
      String textMessage,
      final ReplyKeyboardMarkup replyKeyboardMarkup) {
    final SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(textMessage);
    if (replyKeyboardMarkup != null) {
      sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
    return sendMessage;
  }
}
