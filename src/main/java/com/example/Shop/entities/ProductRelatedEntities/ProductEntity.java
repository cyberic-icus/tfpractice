package com.example.Shop.entities.ProductRelatedEntities;


//import com.example.Shop.entities.CartEntity;
//import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.entities.CartEntity;
import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.entities.OrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    public Long id;
    public String name;
    public String description;
    public double price;
    @CreatedDate public Instant createdDate;

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

    public List<ProductDataEntity> getSizesAndColors() {
        return sizesAndColors;
    }


    public ProductEntity(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }


//    public CategoryEntity getCategoryEntity() {
//        return categoryEntity;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }


//    public void setCategoryEntity(CategoryEntity categoryEntity) {
//        this.categoryEntity = categoryEntity;
//    }
}
