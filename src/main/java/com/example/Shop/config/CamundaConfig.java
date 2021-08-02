package com.example.Shop.config;


import com.example.Shop.config.beans.TelegramPostService;
import com.example.Shop.config.beans.TestService;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.ProcessEngineService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.spring.application.SpringProcessApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {
    @Bean
    public TelegramPostService binSendMsgTelegram() {
        return new TelegramPostService();
    }

    @Bean
    public TestService binSendMsgSomwhere() {
        return new TestService();
    }
}
