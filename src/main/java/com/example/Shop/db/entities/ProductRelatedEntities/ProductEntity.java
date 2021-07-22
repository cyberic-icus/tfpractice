package com.example.Shop.db.entities.ProductRelatedEntities;

import com.example.Shop.db.entities.UserRelatedEntities.CartEntity;
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
    public Long price;
    public String imageUrl;

    @CreatedDate
    public Instant createdDate;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    List<ProductDataEntity> sizesAndColors = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<CartEntity> productCarts = new ArrayList<>();
}
