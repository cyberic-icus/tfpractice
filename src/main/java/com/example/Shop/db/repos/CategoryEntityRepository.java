package com.example.Shop.db.repos;

import com.example.Shop.db.entities.ProductRelatedEntities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryEntityRepository extends CrudRepository<CategoryEntity, Long> {
}
