package com.example.ShopBot.component;

import com.example.ShopBot.config.BotConfig;
import com.example.ShopBot.model.OrderModel;
import com.example.ShopBot.model.ProductQuantityModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Component
public class Bot extends TelegramLongPollingBot {

    final BotConfig config;

    public Bot(BotConfig config) {
        this.config = config;
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        String messageText;
        String chatId;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            builder.chatId(chatId);
            messageText = update.getMessage().getText();
        } else {
            chatId = update.getChannelPost().getChatId().toString();
            builder.chatId(chatId);
            messageText = update.getChannelPost().getText();
        }

        if (messageText.contains("/hello")) {
            builder.text("Привет");
            try {
                execute(builder.build());
            } catch (TelegramApiException e) {
            }
        }

        if (messageText.contains("/chartId")) {
            builder.text("ID Канала : " + chatId);
            try {
                execute(builder.build());
            } catch (TelegramApiException e) {
            }
        }
        if (messageText.contains("/orders")) {
            try {

                URL url = new URL("https://tfpractice.herokuapp.com/order");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responsecode = conn.getResponseCode();
                builder.text(String.valueOf(conn.getResponseCode()));
                try {
                    execute(builder.build());
                } catch (TelegramApiException e) {
                    builder.text("something went wrong");
                    builder.text(e.getMessage());

                }
                if (responsecode == 200) {
                    String inline = "";
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        inline += scanner.nextLine();
                    }


                    scanner.close();

                    Gson g = new Gson();


                    List<OrderModel> orderModelList = g.fromJson(inline, new TypeToken<List<OrderModel>>(){}.getType());
                    StringBuilder orderMessage = new StringBuilder();
                    for(OrderModel orderModel: orderModelList){
                        System.out.println(orderModel.getPrice());
                        orderMessage.append("Заказ ").append(orderModel.getOrderId()).append("\n");
                        orderMessage.append("На сумму: ").append(orderModel.getPrice()).append(" руб.").append("\n");
                        orderMessage.append("Адрес доставки: ").append(orderModel.getOrderDestination()).append("\n");
                        orderMessage.append("Заказ выполнен: ").append(orderModel.getCompleted()).append("\n");
                        orderMessage.append("Заказчик: ").append(orderModel.getCustomer().getUserFirstname()).append(" ").append(orderModel.getCustomer().getUserLastname()).append("\n");
                        orderMessage.append("Телефон: ").append(orderModel.getCustomer().getUserPhoneNumber()).append("\n");
                        orderMessage.append("Email: ").append(orderModel.getCustomer().getUserEmail()).append("\n");
                        for(ProductQuantityModel productQuantityModel: orderModel.getProductList()){
                            orderMessage.append("Размер и цвет: ").append(String.valueOf(productQuantityModel.getProductDataId())).append("\n");
                            orderMessage.append("Количество: ").append(String.valueOf(productQuantityModel.getQuantity())).append("\n");
                        }
                        builder.text(orderMessage.toString());
                        try {
                            execute(builder.build());
                        } catch (TelegramApiException e) {
                        }
                    }


                }
            } catch (Exception e) {
                builder.text("something went wrong");
                builder.text(e.getMessage());

                try {
                    execute(builder.build());
                } catch (TelegramApiException ee) {
                    builder.text("something went wrong");
                    builder.text(ee.getMessage());
                    builder.text(ee.getCause().toString());
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
