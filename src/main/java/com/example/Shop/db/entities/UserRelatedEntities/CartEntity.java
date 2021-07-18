package com.example.Shop.db.entities.UserRelatedEntities;


import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"cart_products_list"})
@JsonPropertyOrder({"cart_id", "cart_products_list"})
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("cart_id")
    public Long id;

    @JsonProperty("cart_products_list")
    @JsonManagedReference(value = "cart-test")
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Set<ProductEntity> cartProducts = new HashSet<>();

    @JsonBackReference(value = "usercart-test")
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public UserEntity cartUserEntity;

    public CartEntity(Set<ProductEntity> cartProducts, UserEntity cartUserEntity) {
        this.cartProducts = cartProducts;
        this.cartUserEntity = cartUserEntity;
    }

    public Set<ProductEntity> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Set<ProductEntity> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getCartUserEntity() {
        return cartUserEntity;
    }

    public void setCartUserEntity(UserEntity cartUserEntity) {
        this.cartUserEntity = cartUserEntity;
    }
}