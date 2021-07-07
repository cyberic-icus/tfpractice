package com.example.Shop.entities;

import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class CategoryEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long id;
//    public String name;
//    public String description;
//
//    @OneToMany Set<ProductEntity> productEntitySet;
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public Set<ProductEntity> getProductEntitySet() {
//        return productEntitySet;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setProductEntitySet(Set<ProductEntity> productEntitySet) {
//        this.productEntitySet = productEntitySet;
//    }
//
//}
