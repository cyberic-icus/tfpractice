package com.example.ShopBot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductEndModel {
    ProductModel product;
    List<ProductQuantityModel> details;
}
