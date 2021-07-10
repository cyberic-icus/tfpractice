package com.example.Shop.entities.UserRelatedEntities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

public enum UserRole implements GrantedAuthority {
    USER,
    ADMIN,
    BOT,
    SERVICE;


    @Override
    public String getAuthority() {
        return "ROLE_"+name();
    }
}