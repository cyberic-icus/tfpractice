package com.example.Shop.db.entities.UserRelatedEntities;


import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonManagedReference(value = "cart-test")
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public Set<ProductEntity> cartProducts = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn
    public UserEntity cartUserEntity;
}