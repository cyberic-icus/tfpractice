package com.example.ShopBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
public class BotConfig {

    @Value("${botUserName}")
    String botUserName;

    @Value("${token}")
    String token;
}