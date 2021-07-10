package com.example.Shop.entities.UserRelatedEntities;


import com.example.Shop.entities.CartEntity;
import com.example.Shop.entities.OrderEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({"user_id", "user_firstname", "user_lastname", "user_username", "user_date_joined","user_authorities_list", "user_cart", "user_orders_list", "enabled", "accountNonExpired","accountNonLocked","credentialsNonExpired"})
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    public Long id;
    @JsonProperty("user_firstname")
    public String firstName;
    @JsonProperty("user_lastname")
    public String lastName;
    @JsonProperty("user_username")
    public String username;

    @JsonProperty("user_date_joined")
    @CreatedDate public Instant dateJoined;

    private String password;

    @JsonProperty("user_authorities_list")
    @JsonManagedReference(value = "userauthorities-test")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAuthority> authorities = new HashSet<>();

    @JsonProperty("user_orders_list")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderEntity> orders = new HashSet<>();

    @JsonProperty("user_cart")
    @JsonManagedReference(value="usercart-test")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    CartEntity cartEntity = new CartEntity();

    public void grantRole(UserRole role) {
        if (authorities == null) {
            authorities = new HashSet<UserAuthority>();
        }
        authorities.add(new UserAuthority(this, role));
    }

    public UserEntity(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }


    public Set<UserAuthority> iDontUnderstandGrantedAuthorityShit(){
        return authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Instant getDateJoined() {
        return dateJoined;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateJoined(Instant dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}