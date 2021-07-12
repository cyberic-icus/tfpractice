package com.example.Shop.db.repos;

import com.example.Shop.db.entities.UserRelatedEntities.UserAuthority;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {

}
