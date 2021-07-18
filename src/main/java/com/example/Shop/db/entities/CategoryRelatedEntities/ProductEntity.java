package com.example.Shop.db.entities.CategoryRelatedEntities;

import com.example.Shop.db.entities.UserRelatedEntities.CartEntity;
import com.example.Shop.db.entities.UserRelatedEntities.OrderEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String productName;
    public String productDescription;
    public double price;
    public String imageUrl;


    @CreatedDate
    public Instant createdDate;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<ProductDataEntity> sizesAndColors = new ArrayList<>();

    @JsonBackReference(value = "order-test")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<OrderEntity> productOrders = new ArrayList<>();

    @JsonBackReference(value = "category-test")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<CategoryEntity> productCategories = new ArrayList<>();

    @JsonBackReference(value = "cart-test")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn
    List<CartEntity> productCarts = new ArrayList<>();
}
