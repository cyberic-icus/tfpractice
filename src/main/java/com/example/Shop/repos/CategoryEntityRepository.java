package com.example.Shop.repos;

import com.example.Shop.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryEntityRepository extends CrudRepository<CategoryEntity, Long> {
}
