package com.example.Shop.db.repos;

import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
