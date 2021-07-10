package com.example.Shop.entities.ProductRelatedEntities;


//import com.example.Shop.entities.CartEntity;
//import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.entities.CartEntity;
import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.entities.OrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
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

    @JsonProperty("sizes_and_colors_list")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductDataEntity> sizesAndColors = new ArrayList<>();

    @JsonBackReference(value="order-test")
    @ManyToOne(cascade = CascadeType.ALL)
    OrderEntity orderEntity;

    @JsonBackReference(value="category-test")
    @ManyToOne(cascade = CascadeType.ALL)
    CategoryEntity categoryEntity;

    @JsonBackReference(value="cart-test")
    @ManyToOne(cascade = CascadeType.ALL)
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

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }
}
