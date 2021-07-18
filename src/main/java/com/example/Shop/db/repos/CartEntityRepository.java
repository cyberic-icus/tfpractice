package com.example.Shop.db.repos;

import com.example.Shop.db.entities.UserRelatedEntities.CartEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartEntityRepository extends CrudRepository<CartEntity, Long> {
}
