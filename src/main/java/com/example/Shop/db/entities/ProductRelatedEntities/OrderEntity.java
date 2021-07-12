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
@JsonIgnoreProperties({"order_products_list"})
@JsonPropertyOrder({"order_id", "order_products_list"})
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("order_id")
    public Long id;

    @JsonProperty("order_products_list")
    @JsonManagedReference(value="order-test")
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Set<ProductEntity> productEntitySet = new HashSet<>();

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    UserEntity userEntity;


    public Long getId() {
        return id;
    }


    public Set<ProductEntity> getProductEntitySet() {
        return productEntitySet;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setProductEntitySet(Set<ProductEntity> productEntitySet) {
        this.productEntitySet = productEntitySet;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public OrderEntity(Set<ProductEntity> productEntitySet, UserEntity userEntity) {
        this.productEntitySet = productEntitySet;
        this.userEntity = userEntity;
    }
}