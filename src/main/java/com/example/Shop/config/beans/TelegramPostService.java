package com.example.Shop.config.beans;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class TelegramPostService implements JavaDelegate {
    public void execute(DelegateExecution delegate) {
        System.out.println("Send to tg");
    }
}