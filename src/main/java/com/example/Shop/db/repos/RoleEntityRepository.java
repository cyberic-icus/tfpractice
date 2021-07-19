package com.example.Shop.db.repos;

import com.example.Shop.db.entities.UserRelatedEntities.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleEntityRepository extends CrudRepository<RoleEntity, Long> {
}
