package com.example.ShopBot.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductQuantityModel {
    private Long productQuantityId;
    private Long productDataId;
    private Long quantity;
}
