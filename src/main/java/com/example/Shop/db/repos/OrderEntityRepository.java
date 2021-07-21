package com.example.Shop.db.repos;

import com.example.Shop.db.entities.OrderRelatedEntites.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderEntityRepository extends CrudRepository<OrderEntity, Long> {
}
