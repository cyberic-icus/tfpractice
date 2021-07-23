package com.example.ShopBot.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductModel {
    private Long productId;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private String productImageUrl;
}
