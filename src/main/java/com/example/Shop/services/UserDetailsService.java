package com.example.Shop.services;

import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.repos.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public final UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = userEntityRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        detailsChecker.check(user);
        return user;
    }
}