package com.example.ShopBot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class OrderModel {
    private Long orderId;
    private String orderDestination;
    private CustomerModel customer;
    private Long price;
    private Boolean completed;
    private List<ProductQuantityModel> productList;
}

