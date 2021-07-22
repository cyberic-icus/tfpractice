package com.example.Shop.db.entities.UserRelatedEntities;


import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
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

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn
    public Set<ProductEntity> cartProducts = new HashSet<>();

//    @JoinColumn
//    public UserEntity cartUserEntity;
}