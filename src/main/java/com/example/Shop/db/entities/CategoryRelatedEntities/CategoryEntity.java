package com.example.Shop.db.entities.CategoryRelatedEntities;

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
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String categoryName;
    public String categoryDescription;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    Set<ProductEntity> categoryProductEntitySet = new HashSet<>();

}
