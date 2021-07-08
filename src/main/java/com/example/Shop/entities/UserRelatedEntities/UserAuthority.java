package com.example.Shop.entities.UserRelatedEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class UserAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    private UserRole authority;

    @Override
    public String getAuthority() {
        return null;
    }

    public UserAuthority(UserEntity user, UserRole authority) {
        this.user = user;
        this.authority = authority;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setAuthority(UserRole authority) {
        this.authority = authority;
    }

    public UserEntity getUser() {
        return user;
    }
}