package com.example.Shop.db.repos;

import com.example.Shop.db.entities.OrderRelatedEntites.ProductQuantityEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductQuantityRepository extends CrudRepository<ProductQuantityEntity, Long> {
}
