package com.bvg.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class BotConfig {

    // Имя бота, заданное при регистрации
    @Value("${bot.botUserName}")
    String botUserName;

    // Токен, полученный при регистрации
    @Value("${bot.token}")
    String token;
}
