package com.example.Shop.db.entities.ProductRelatedEntities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"sizes_and_colors_list"})
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({"product_id", "product_name", "product_description", "product_price", "product_created_on", "sizes_and_colors_list" })
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("product_id")
    public Long id;
    @JsonProperty("product_name")
    public String name;
    @JsonProperty("product_description")
    public String description;
    @JsonProperty("product_price")
    public double price;
    @JsonProperty("product_created_on")
    @CreatedDate public Instant createdDate;

    @JsonProperty("product_image_url")
    public String imageUrl;

    @JsonProperty("sizes_and_colors_list")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    List<ProductDataEntity> sizesAndColors = new ArrayList<>();

    @JsonBackReference(value="order-test")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    OrderEntity orderEntity;

    @JsonBackReference(value="category-test")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    CategoryEntity categoryEntity;

    @JsonBackReference(value="cart-test")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    CartEntity cartEntity;

    public ProductEntity(String name) {
        this.name = name;
    }

    public ProductEntity(String name, String description) {
        this(name);
        this.description = description;
    }

    public ProductEntity(String name, String description, double price) {
        this(name, description);
        this.price = price;
    }

    public ProductEntity(String name, String description, double price, List<ProductDataEntity> sizesAndColors) {
        this(name, description, price);
        this.sizesAndColors = sizesAndColors;
    }

    public ProductEntity(String name, String description, double price, List<ProductDataEntity> sizesAndColors, CategoryEntity categoryEntity) {
        this(name, description, price, sizesAndColors);
        this.categoryEntity = categoryEntity;
    }

    public ProductEntity(String name, String description, double price, List<ProductDataEntity> sizesAndColors, CategoryEntity categoryEntity, CartEntity cartEntity) {
        this(name, description, price, sizesAndColors, categoryEntity);
        this.cartEntity = cartEntity;
    }

    public ProductEntity(String name, String description, double price, List<ProductDataEntity> sizesAndColors, OrderEntity orderEntity, CategoryEntity categoryEntity, CartEntity cartEntity) {
        this(name, description, price, sizesAndColors, categoryEntity, cartEntity);
        this.orderEntity = orderEntity;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public List<ProductDataEntity> getSizesAndColors() {
        return sizesAndColors;
    }

    public void setSizesAndColors(List<ProductDataEntity> sizesAndColors) {
        this.sizesAndColors = sizesAndColors;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public ProductEntity(String name, String description, double price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }
}