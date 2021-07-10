package com.example.Shop.entities.ProductRelatedEntities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("product_data_id")
    public Long id;
    public String color;
    public int size;
    public int quantity;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    public ProductEntity productEntity;

    public ProductDataEntity(String color, int size, int quantity, ProductEntity productEntity) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.productEntity = productEntity;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }


    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public ProductDataEntity(String color) {
        this.color = color;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductDataEntity(String color, int size, int quantity) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }
}
