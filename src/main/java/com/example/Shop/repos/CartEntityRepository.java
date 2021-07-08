package com.example.Shop.repos;

import com.example.Shop.entities.CartEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartEntityRepository extends CrudRepository<CartEntity, Long> {
}
