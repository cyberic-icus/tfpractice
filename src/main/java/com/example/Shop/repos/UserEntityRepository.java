package com.example.Shop.repos;

import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
