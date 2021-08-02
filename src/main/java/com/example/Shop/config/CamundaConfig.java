package com.example.Shop.config;


import com.example.Shop.config.beans.TelegramPostService;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.ProcessEngineService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.spring.application.SpringProcessApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {
//    @Bean
//    public ProcessEngineService processEngineService() {
//        return BpmPlatform.getProcessEngineService();
//    }
//
//    @Bean(destroyMethod = "")
//    public ProcessEngine processEngine(){
//        return BpmPlatform.getDefaultProcessEngine();
//    }
//
//    @Bean
//    public SpringProcessApplication processApplication() {
//        return new SpringProcessApplication();
//    }
//
//    @Bean
//    public RepositoryService repositoryService(ProcessEngine processEngine) {
//        return processEngine.getRepositoryService();
//    }

//    @Bean
//    public RuntimeService runtimeService(ProcessEngine processEngine) {
//        return processEngine.getRuntimeService();
//    }

//    @Bean
//    public TaskService taskService(ProcessEngine processEngine) {
//        return processEngine.getTaskService();
//    }
//
//    @Bean
//    public HistoryService historyService(ProcessEngine processEngine) {
//        return processEngine.getHistoryService();
//    }
//
//    @Bean
//    public ManagementService managementService(ProcessEngine processEngine) {
//        return processEngine.getManagementService();
//    }

    @Bean
    public TelegramPostService binSendMsgTelegram() {
        return new TelegramPostService();
    }
}
