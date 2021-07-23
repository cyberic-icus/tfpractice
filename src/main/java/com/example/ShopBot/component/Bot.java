package com.example.ShopBot.component;

import com.example.ShopBot.config.BotConfig;
import com.example.ShopBot.model.OrderModel;
import com.example.ShopBot.model.ProductEndModel;
import com.example.ShopBot.model.ProductModel;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
        if (update.getMessage().hasText()) {
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
                    if (responsecode == 200) {
                        builder.text("Ответ от сервера:");
                    } else {
                        builder.text("У сервера возникли проблемы.");
                        builder.text(String.valueOf(conn.getResponseCode()));
                    }
                    try {
                        execute(builder.build());
                    } catch (TelegramApiException e) {
                        builder.text("something went wrong");
                        builder.text(e.getMessage());

                    }
                    if (responsecode == 200) {
                        StringBuilder orderMessage = new StringBuilder();
                        String inline = "";
                        Scanner scanner = new Scanner(url.openStream());

                        while (scanner.hasNext()) {
                            inline += scanner.nextLine();
                        }


                        scanner.close();

                        Gson g = new Gson();


                        List<OrderModel> orderModelList = g.fromJson(inline, new TypeToken<List<OrderModel>>() {}.getType());
                        for (OrderModel orderModel : orderModelList) {
                            orderMessage.append("Заказ №").append(orderModel.getOrderId()).append("\n");
                            orderMessage.append("На сумму: ").append(orderModel.getPrice()).append(" руб.").append("\n");
                            orderMessage.append("Состояние заказа: ").append(orderModel.getOrderState()).append("\n");
                            orderMessage.append("Заказ выполнен: ").append(orderModel.getCompleted()).append("\n");
                            orderMessage.append("Заказ оплачен: ").append(orderModel.getIsPaid()).append("\n");
                            orderMessage.append("Адрес доставки: ").append(orderModel.getOrderDestination()).append("\n");
                            orderMessage.append("Заказчик: ").append(orderModel.getCustomer().getUserFirstname()).append(" ").append(orderModel.getCustomer().getUserLastname()).append("\n");
                            orderMessage.append("Телефон: ").append(orderModel.getCustomer().getUserPhoneNumber()).append("\n");
                            orderMessage.append("Email: ").append(orderModel.getCustomer().getUserEmail()).append("\n");
                            orderMessage.append("Товары: ").append("\n").append("\n");
                            for (ProductEndModel productEndModel : orderModel.getProductList()) {
                                ProductModel productModel = productEndModel.getProduct();
                                orderMessage.append("Товар: ").append(productModel.getProductName()).append(" (ID: ").append(productModel.getProductId()).append(")").append("\n");
                                for (ProductQuantityModel productQuantityModel : productEndModel.getDetails()) {
                                    orderMessage.append("Цвет: ").append(productQuantityModel.getProductDataColor()).append("\n");
                                    orderMessage.append("Размер: ").append(productQuantityModel.getProductDataSize()).append("\n");
                                    orderMessage.append("Количество: ").append(productQuantityModel.getProductDataQuantity()).append("\n\n");
                                }
                            }
                            try {
                                String message = orderMessage.toString();
                                System.out.println(message);
                                builder.text(message);
                                execute(builder.build());
                            } catch (TelegramApiException e) {
                                System.out.println(e);
                                System.out.println(e.getMessage());
                                System.out.println(e.getCause().toString());
                            }
                        }


                    }
                } catch (Exception e) {
                    builder.text("something went wrong");
                    builder.text(e.getMessage());

                    try {
                        execute(builder.build());
                    } catch (TelegramApiException ee) {
                        System.out.println(e);
                        System.out.println(e.getMessage());
                        System.out.println(e.getCause().toString());
                    }
                }
            } else if (update.hasCallbackQuery()) {

            }
        }
    }

//    public static SendMessage sendInlineKeyBoardMessage(String mes, String chatId) {
//
//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//        List<InlineKeyboardButton> rowInline = new ArrayList<>();
//        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//        inlineKeyboardButton.setText("Some button");
//        rowInline.add(inlineKeyboardButton);
//        rowsInline.add(rowInline);
//        markupInline.setKeyboard(rowsInline);
//
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText(mes);
//        message.setReplyMarkup(markupInline);
//
//
//        return message;
//    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
