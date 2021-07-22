package com.example.Shop.db.entities.ProductRelatedEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String color;
    public Long size;
    public Long quantity;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public ProductEntity productEntity;

}
