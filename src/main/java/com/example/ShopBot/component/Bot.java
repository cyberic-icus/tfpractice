package com.example.ShopBot.component;

import com.example.ShopBot.config.BotConfig;
import com.example.ShopBot.dto.OrderCompletedDTO;
import com.example.ShopBot.dto.OrderStateDTO;
import com.example.ShopBot.model.OrderModel;
import com.example.ShopBot.model.ProductEndModel;
import com.example.ShopBot.model.ProductModel;
import com.example.ShopBot.model.ProductQuantityModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Bot extends TelegramLongPollingBot {

    final BotConfig config;
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public Bot(BotConfig config) {
        this.config = config;
    }

    public List<OrderModel> getAllOrders() throws IOException {

        URL url = new URL("https://tfpractice.herokuapp.com/order");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();
        if (responsecode == 200) {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }


            scanner.close();

            Gson g = new Gson();
            return g.fromJson(inline, new TypeToken<List<OrderModel>>() {
            }.getType());
        }
        return null;
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {

        update.getUpdateId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.enableMarkdown(true);

        String messageText;
        String chatId;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            messageText = update.getMessage().getText();
        } else {
            chatId = update.getChannelPost().getChatId().toString();
            messageText = update.getChannelPost().getText();
        }


        if (messageText.contains("/chartId")) {
            try {
                sendMessage.setText(chatId);
                execute(sendMessage);
            } catch (TelegramApiException e) {
            }
        }

            ArrayList<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardRow1 = new KeyboardRow();
            KeyboardRow keyboardRow2 = new KeyboardRow();

            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            replyKeyboardMarkup.setResizeKeyboard(true);

            keyboardRow1.add(new KeyboardButton("Все"));
            keyboardRow1.add(new KeyboardButton("Оплаченные"));
            keyboardRow1.add(new KeyboardButton("Выполненные"));
            keyboardRow2.add(new KeyboardButton("Завершить"));
            keyboardRow2.add(new KeyboardButton("Изменить состояние"));

            keyboard.add(keyboardRow1);
            keyboard.add(keyboardRow2);

            replyKeyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

        if (messageText.contains("Завершить")) {
            if (messageText.equals("Завершить")) {
                try {
                    SendMessage mes = new SendMessage();
                    mes.setText("Неверная семантика! Введите id\nЗавершить {id}");
                    execute(mes);
                } catch (Exception e) {
                }
            } else {
                String mes = messageText;
                Long id = Long.valueOf(mes.substring(16));
                Gson g = new Gson();
                OrderCompletedDTO orderCompletedDTO = new OrderCompletedDTO(true);
                String json = g.toJson(orderCompletedDTO);


                URL url = new URL("https://tfpractice.herokuapp.com/order/" + mes.substring(16) + "/complete/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(json);
                osw.flush();
                osw.close();
                System.err.println(connection.getResponseCode());
            }
        }
        if (messageText.contains("Изменить состояние")) {
            if (messageText.equals("Изменить состояние")) {
                try {
                    SendMessage mes = new SendMessage();
                    mes.setText("Неверная семантика! Введите состояние\nИзменить состояние {состояние}");
                    execute(mes);
                } catch (Exception e) {
                }
            } else {
                String mes = messageText;
                String state = mes.substring(19);

                Gson g = new Gson();
                OrderStateDTO orderStateDTO = new OrderStateDTO(state);
                String json = g.toJson(orderStateDTO);

                URL url = new URL("https://tfpractice.herokuapp.com/order/" + mes.substring(16) + "/state/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(json);
                osw.flush();
                osw.close();
                System.err.println(connection.getResponseCode());
            }
        }
        if (messageText.contains("Оплаченные")) {
            try {
                List<OrderModel> orderModelList = getAllOrders().stream()
                        .filter(OrderModel::getIsPaid)
                        .collect(Collectors.toList());
                for (OrderModel orderModel : orderModelList) {
//
//                    InlineKeyboardMarkup mesbuts = new InlineKeyboardMarkup();
//                    InlineKeyboardButton bsetState = new InlineKeyboardButton();
//                    bsetState.setText("Изменить состояние");
//                    InlineKeyboardButton bsetCompleted = new InlineKeyboardButton();
//                    bsetCompleted.setText("Завершить заказ");
//
//                    List<InlineKeyboardButton> r1 = new ArrayList<>();
//                    r1.add(bsetState);
//                    r1.add(bsetCompleted);
//
//                    List<List<InlineKeyboardButton>> rf = new ArrayList<>();
//                    rf.add(r1);
//
//                    mesbuts.setKeyboard(rf);
//
//
//                    bsetState.setCallbackData(String.valueOf(orderModel.getOrderId()));

                    StringBuilder orderMessage = new StringBuilder();
                    orderMessage.append("Заказ №").append(orderModel.getOrderId()).append("\n");
                    orderMessage.append("На сумму: ").append(orderModel.getPrice()).append(" руб.").append("\n");
                    orderMessage.append("Состояние заказа: ").append(orderModel.getOrderState()).append("\n");
                    if (orderModel.getCompleted())
                        orderMessage.append("Заказ выполнен: да").append("\n");
                    else orderMessage.append("Заказ выполнен: нет").append("\n");
                    orderMessage.append("Адрес доставки: ").append(orderModel.getOrderDestination()).append("\n");
                    orderMessage.append("Заказчик: ").append(orderModel.getCustomer().getUserFirstname()).append(" ").append(orderModel.getCustomer().getUserLastname()).append("\n");
                    orderMessage.append("Телефон: ").append(orderModel.getCustomer().getUserPhoneNumber()).append("\n");
                    orderMessage.append("Email: ").append(orderModel.getCustomer().getUserEmail()).append("\n").append("\n");
                    orderMessage.append("Товары: ").append("\n").append("\n");
                    for (ProductEndModel productEndModel : orderModel.getProductList()) {
                        ProductModel productModel = productEndModel.getProduct();
                        orderMessage.append("Название: ").append(productModel.getProductName()).append(" (ID: ").append(productModel.getProductId()).append(")").append("\n");
                        for (ProductQuantityModel productQuantityModel : productEndModel.getDetails()) {
                            orderMessage.append("Цвет: ").append(productQuantityModel.getProductDataColor()).append("\n");
                            orderMessage.append("Размер: ").append(productQuantityModel.getProductDataSize()).append("\n");
                            orderMessage.append("Количество: ").append(productQuantityModel.getProductDataQuantity()).append("\n\n");
                        }
                    }

                    try {
                        String message = orderMessage.toString();
                        System.out.println(message);
                        sendMessage.setText(message);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                        System.out.println(e.getMessage());
                        System.out.println(e.getCause().toString());
                    }
                }


            } catch (Exception e) {
            }
        }

        if (messageText.contains("Выполненные")) {
            try {
                List<OrderModel> orderModelList = getAllOrders().stream()
                        .filter(OrderModel::getCompleted)
                        .collect(Collectors.toList());
                for (OrderModel orderModel : orderModelList) {
                    StringBuilder orderMessage = new StringBuilder();
                    orderMessage.append("Заказ №").append(orderModel.getOrderId()).append("\n");
                    orderMessage.append("На сумму: ").append(orderModel.getPrice()).append(" руб.").append("\n");
                    orderMessage.append("Состояние заказа: ").append(orderModel.getOrderState()).append("\n");
                    orderMessage.append("Адрес доставки: ").append(orderModel.getOrderDestination()).append("\n");
                    orderMessage.append("Заказчик: ").append(orderModel.getCustomer().getUserFirstname()).append(" ").append(orderModel.getCustomer().getUserLastname()).append("\n");
                    orderMessage.append("Телефон: ").append(orderModel.getCustomer().getUserPhoneNumber()).append("\n");
                    orderMessage.append("Email: ").append(orderModel.getCustomer().getUserEmail()).append("\n").append("\n");
                    orderMessage.append("Товары: ").append("\n").append("\n");
                    for (ProductEndModel productEndModel : orderModel.getProductList()) {
                        ProductModel productModel = productEndModel.getProduct();
                        orderMessage.append("Название: ").append(productModel.getProductName()).append(" (ID: ").append(productModel.getProductId()).append(")").append("\n");
                        for (ProductQuantityModel productQuantityModel : productEndModel.getDetails()) {
                            orderMessage.append("Цвет: ").append(productQuantityModel.getProductDataColor()).append("\n");
                            orderMessage.append("Размер: ").append(productQuantityModel.getProductDataSize()).append("\n");
                            orderMessage.append("Количество: ").append(productQuantityModel.getProductDataQuantity()).append("\n\n");
                        }
                    }

                    try {
                        String message = orderMessage.toString();
                        System.out.println(message);
                        sendMessage.setText(message);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                        System.out.println(e.getMessage());
                        System.out.println(e.getCause().toString());
                    }
                }


            } catch (Exception e) {
            }
        }

        if (messageText.contains("Все")) {
            try {
                List<OrderModel> orderModelList = getAllOrders();
                for (OrderModel orderModel : orderModelList) {
                    StringBuilder orderMessage = new StringBuilder();
                    orderMessage.append("Заказ №").append(orderModel.getOrderId()).append("\n");
                    orderMessage.append("На сумму: ").append(orderModel.getPrice()).append(" руб.").append("\n");
                    orderMessage.append("Состояние заказа: ").append(orderModel.getOrderState()).append("\n");
                    if (orderModel.getCompleted())
                        orderMessage.append("Заказ выполнен: да").append("\n");
                    else orderMessage.append("Заказ выполнен: нет").append("\n");
                    if (orderModel.getIsPaid()) orderMessage.append("Заказ оплачен: да").append("\n");
                    else orderMessage.append("Заказ оплачен: нет").append("\n");
                    orderMessage.append("Заказ оплачен: ").append(orderModel.getIsPaid()).append("\n");
                    orderMessage.append("Адрес доставки: ").append(orderModel.getOrderDestination()).append("\n");
                    orderMessage.append("Заказчик: ").append(orderModel.getCustomer().getUserFirstname()).append(" ").append(orderModel.getCustomer().getUserLastname()).append("\n");
                    orderMessage.append("Телефон: ").append(orderModel.getCustomer().getUserPhoneNumber()).append("\n");
                    orderMessage.append("Email: ").append(orderModel.getCustomer().getUserEmail()).append("\n").append("\n");
                    orderMessage.append("Товары: ").append("\n").append("\n");
                    for (ProductEndModel productEndModel : orderModel.getProductList()) {
                        ProductModel productModel = productEndModel.getProduct();
                        orderMessage.append("Название: ").append(productModel.getProductName()).append(" (ID: ").append(productModel.getProductId()).append(")").append("\n");
                        for (ProductQuantityModel productQuantityModel : productEndModel.getDetails()) {
                            orderMessage.append("Цвет: ").append(productQuantityModel.getProductDataColor()).append("\n");
                            orderMessage.append("Размер: ").append(productQuantityModel.getProductDataSize()).append("\n");
                            orderMessage.append("Количество: ").append(productQuantityModel.getProductDataQuantity()).append("\n\n");
                        }
                    }

                    try {
                        String message = orderMessage.toString();
                        System.out.println(message);
                        sendMessage.setText(message);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println(e);
                        System.out.println(e.getMessage());
                        System.out.println(e.getCause().toString());
                    }
                }


            } catch (Exception e) {
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
