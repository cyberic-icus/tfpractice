package com.example.Shop.entities;


//import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
//import com.example.Shop.entities.UserRelatedEntities.UserEntity;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Set;

//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class CartEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long id;
//    @OneToMany public Set<ProductEntity> products;
//    @OneToOne(cascade = CascadeType.ALL)
//    public UserEntity userEntity;
//
//    public void setProducts(Set<ProductEntity> products) {
//        this.products = products;
//    }
//
//    public Set<ProductEntity> getProducts() {
//        return products;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}
