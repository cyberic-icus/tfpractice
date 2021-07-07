package com.example.Shop.repos;

import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductDataEntityRepository extends CrudRepository<ProductDataEntity, Long> {
}
