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
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({"user_id", "user_firstname", "user_lastname", "user_username","user_email", "user_phone_number", "user_order_dist","user_date_joined","user_roles_list", "user_cart", "user_orders_list", "user_orders_history_list", "enabled", "accountNonExpired","accountNonLocked","credentialsNonExpired"})
public class UserEntity{

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
    @JsonProperty("user_email")
    private String email;
    @JsonProperty("user_phone_number")
    private String phoneNumber;
    @JsonProperty("user_order_dist")
    private String location;

    @JsonProperty("user_roles_list")
    @JsonManagedReference(value = "userauthorities-test")
    @JoinColumn
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAuthority> roles = new HashSet<>();

    @JsonProperty("user_orders_list")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderEntity> orders = new HashSet<>();

    @JsonProperty("user_orders_history_list")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderEntity> orders_history = new ArrayList<>();

    @JsonProperty("user_cart")
    @JsonManagedReference(value="usercart-test")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    CartEntity cartEntity = new CartEntity();


    public UserEntity(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserAuthority> roles) {
        this.roles = roles;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<OrderEntity> getOrders_history() {
        return orders_history;
    }

    public void setOrders_history(List<OrderEntity> orders_history) {
        this.orders_history = orders_history;
    }

    public UserEntity(String firstName, String lastName, String username, String password, String email, String phoneNumber, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }
}