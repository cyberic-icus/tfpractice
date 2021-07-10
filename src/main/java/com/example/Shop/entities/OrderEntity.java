package com.example.Shop.entities;


import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.entities.UserRelatedEntities.UserAuthority;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"order_id", "order_products_list"})
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("order_id")
    public Long id;

    @JsonProperty("order_products_list")
    @JsonManagedReference(value="order-test")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ProductEntity> productEntitySet = new HashSet<>();

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
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