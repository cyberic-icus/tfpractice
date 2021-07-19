package com.example.Shop.db.entities.UserRelatedEntities;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"user_roles_list", "user_orders_list", "user_orders_history_list", "user_cart"})
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({"user_id", "user_firstname", "user_lastname", "user_username", "user_email", "user_phone_number", "user_order_dist", "user_date_joined", "user_roles_list", "user_cart", "user_orders_list", "user_orders_history_list", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 15, message = "firstname too short(big)!(3<x<15)")
    @JsonProperty("user_firstname")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 15, message = "lastname too short(big)!(3<x<15)")
    @JsonProperty("user_lastname")
    private String lastName;

    @NotNull
    @Size(min = 3, max = 15, message = "Username too short(big)!(3<x<15)")
    @JsonProperty("user_username")
    private String username;

    @JsonProperty("user_date_joined")
    @CreatedDate
    private Instant dateJoined;


    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Size(min = 3, max = 15, message = "Username too short(big)!(3<x<15)")
    @JsonProperty("user_email")
    @Email
    private String email;

    @NotNull
    @Size(min = 12, max = 13, message = "Correct phone number pls")
    @JsonProperty("user_phone_number")
    private String phoneNumber;


    @JsonProperty("user_roles_list")
    @JsonManagedReference(value = "userauthorities-test")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<RoleEntity> roles = new HashSet<RoleEntity>();


    @JsonProperty("user_orders_list")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<OrderEntity> orders = new HashSet<>();


    @JsonProperty("user_orders_history_list")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<OrderEntity> orders_history = new ArrayList<>();


    @JsonProperty("user_cart")
    @JsonManagedReference(value = "usercart-test")
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CartEntity cartEntity = new CartEntity();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}