package com.example.Shop.config;


import com.example.Shop.config.beans.CancelOrderService;
import com.example.Shop.config.beans.ShowPaymentService;
import com.example.Shop.config.beans.TelegramPostService;
import com.example.Shop.config.beans.SendToTKService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {
    @Bean
    public TelegramPostService binSendMsgTelegram() {
        return new TelegramPostService();
    }

    @Bean
    public SendToTKService binSendMsgSomwhere() {
        return new SendToTKService();
    }
    @Bean
    public ShowPaymentService binPayment() {
        return new ShowPaymentService();
    }
    @Bean
    public CancelOrderService binCancelOrder() {
        return new CancelOrderService();
    }
}
