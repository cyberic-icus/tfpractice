package com.example.Shop.db.entities.ProductRelatedEntities;

import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"category_products_list"})
@JsonPropertyOrder({"category_id","category_name","category_description","category_products_list"})
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("category_id")
    public Long id;
    @JsonProperty("category_name")
    public String name;
    @JsonProperty("category_description")
    public String description;

    @JsonProperty("category_products_list")
    @JsonManagedReference(value="category-test")
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Set<ProductEntity> productEntitySet = new HashSet<>();


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<ProductEntity> getProductEntitySet() {
        return productEntitySet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductEntitySet(Set<ProductEntity> productEntitySet) {
        this.productEntitySet = productEntitySet;
    }

    public CategoryEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryEntity(String name, String description, Set<ProductEntity> productEntitySet) {
        this.name = name;
        this.description = description;
        this.productEntitySet = productEntitySet;
    }
}
