package com.example.Shop.db.repos;

import com.example.Shop.db.entities.CategoryRelatedEntities.ProductDataEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductDataEntityRepository extends CrudRepository<ProductDataEntity, Long> {
}
