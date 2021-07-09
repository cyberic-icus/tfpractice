package com.example.Shop.entities.UserRelatedEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonBackReference(value = "userauthorities-test")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    private UserRole authority;



    public UserAuthority(UserEntity user, UserRole authority) {
        this.user = user;
        this.authority = authority;
    }

    public UserAuthority(UserRole authority) {
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


    @Override
    public String getAuthority() {
        return authority.toString();
    }
}