package com.example.Shop.services;

import com.example.Shop.db.entities.UserRelatedEntities.UserAuthority;
import com.example.Shop.db.repos.UserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthorityService {
    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    public Optional<UserAuthority> getUserAuthorityById(Long id) {
        return userAuthorityRepository.findById(id);
    }
}
