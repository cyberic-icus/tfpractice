package com.example.Shop.db.entities.OrderRelatedEntites;


import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String destination;
    public Long price;
    public Boolean completed = false;
    public Boolean isPaid = false;
    public String state;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    List<ProductQuantityEntity> orderProductQuantityEntityList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_user_entity_id", nullable = false)
    UserEntity orderUserEntity;

}