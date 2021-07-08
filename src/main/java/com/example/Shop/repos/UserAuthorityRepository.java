package com.example.Shop.repos;

import com.example.Shop.entities.UserRelatedEntities.UserAuthority;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {

}
