package com.example.Shop.db.entities.ProductRelatedEntities;


import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
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
    @JsonManagedReference(value="cart-test")
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Set<ProductEntity> products = new HashSet<>();

    @JsonBackReference(value="usercart-test")
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public UserEntity userEntity;

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public CartEntity(Set<ProductEntity> products, UserEntity userEntity) {
        this.products = products;
        this.userEntity = userEntity;
    }
}
