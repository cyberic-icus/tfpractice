package com.example.Shop.db.entities.ProductRelatedEntities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"product_data_id", "color", "size", "quantity"})
public class ProductDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("product_data_id")
    public Long id;
    public String color;
    public int size;
    public int quantity;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
