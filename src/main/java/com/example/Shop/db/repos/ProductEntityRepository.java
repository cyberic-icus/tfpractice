package com.example.Shop.db.repos;

import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductEntityRepository extends CrudRepository<ProductEntity, Long> {
}
