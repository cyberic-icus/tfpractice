package com.example.Shop.db.repos;

import com.example.Shop.db.entities.CategoryRelatedEntities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryEntityRepository extends CrudRepository<CategoryEntity, Long> {
}
