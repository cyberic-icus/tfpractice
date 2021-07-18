package com.example.Shop.db.entities.UserRelatedEntities;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER,
    ADMIN,
    BOT,
    SERVICE;


    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}