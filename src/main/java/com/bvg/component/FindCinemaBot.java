package com.bvg.component;

import com.bvg.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FindCinemaBot extends TelegramLongPollingBot {

    @Autowired
    private BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<String> filmTitles = new ArrayList<>();
        String userName;
        userName = update.getMessage().getFrom().getFirstName() != null && update.getMessage().getFrom().getLastName() !=null ? update.getMessage().getFrom().getFirstName() + update.getMessage().getFrom().getLastName() : update.getMessage().getFrom().getUserName();
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Create a SendMessage object with mandatory fields
            SendMessage message = SendMessage
                    .builder()
                    .chatId(String.valueOf(update.getMessage().getChatId()))
                    .text(update.getMessage().getText())
                    .build();
            if (update.getMessage().getText().contains("Привет"))
                message.setText("Привет, " + userName);
            if (update.getMessage().getText().contains("Пока"))
                message.setText("Пока, " + userName);
            if (update.getMessage().getText().contains("Покажи список")) {
                message.setText("Запрос пока не обрабатывается");
            }
            // Call method to send the message
            try {
                execute(message);
                log.info("Пользователь " + update.getMessage().getChat().getUserName() + " написал: " + update.getMessage().getText() + ". Бот ответил: " + message.getText());
            } catch (TelegramApiException e) {
                log.error("Ошибка при отправке сообщения пользователю " + update.getMessage().getChat().getUserName(), e.getMessage());
            }
        }
    }
}
