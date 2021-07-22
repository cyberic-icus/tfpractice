package com.example.ShopBot.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class CustomerModel {
    private Long userId;
    private String userFirstname;
    private String userLastname;
    private String userEmail;
    private String userPhoneNumber;
}
