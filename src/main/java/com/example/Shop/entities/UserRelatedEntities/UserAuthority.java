package com.example.Shop.entities.UserRelatedEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public Long id;

    @JsonBackReference(value = "userauthorities-test")
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();

    private UserRole authority;


    public UserAuthority(Set<UserEntity> users, UserRole authority) {
        this.users = users;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public UserAuthority(UserRole authority) {
        this.authority = authority;
    }


    public void setAuthority(UserRole authority) {
        this.authority = authority;
    }



    @Override
    public String getAuthority() {
        return authority.toString();
    }
}