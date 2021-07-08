package com.example.Shop.entities;


import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.entities.UserRelatedEntities.UserAuthority;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

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


}