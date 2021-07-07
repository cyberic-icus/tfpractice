package com.example.Shop.repos;

import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductEntityRepository extends CrudRepository<ProductEntity, Long> {
}
