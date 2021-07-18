package com.example.Shop.db.entities.UserRelatedEntities;


import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
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
    @JsonManagedReference(value = "order-test")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Set<ProductEntity> orderProductEntitySet = new HashSet<>();

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    UserEntity orderUserEntity;


    public OrderEntity(Set<ProductEntity> orderProductEntitySet, UserEntity orderUserEntity) {
        this.orderProductEntitySet = orderProductEntitySet;
        this.orderUserEntity = orderUserEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ProductEntity> getOrderProductEntitySet() {
        return orderProductEntitySet;
    }

    public void setOrderProductEntitySet(Set<ProductEntity> orderProductEntitySet) {
        this.orderProductEntitySet = orderProductEntitySet;
    }

    public UserEntity getOrderUserEntity() {
        return orderUserEntity;
    }

    public void setOrderUserEntity(UserEntity orderUserEntity) {
        this.orderUserEntity = orderUserEntity;
    }
}