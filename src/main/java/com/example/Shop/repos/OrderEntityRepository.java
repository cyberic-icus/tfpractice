package com.example.Shop.repos;

import com.example.Shop.entities.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderEntityRepository extends CrudRepository<OrderEntity, Long> {
}
