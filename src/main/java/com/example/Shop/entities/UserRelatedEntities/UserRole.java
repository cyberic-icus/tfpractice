package com.example.Shop.entities.UserRelatedEntities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

public enum UserRole {
    USER,
    ADMIN,
    BOT,
    SERVICE;

//    @Override
//    public String getAuthority() {
//        return "ROLE_"+name();
//    }
}