package com.example.Shop.services;

import com.example.Shop.db.entities.UserRelatedEntities.RoleEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.db.repos.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserEntityService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserEntityRepository userEntityRepository;

    public List<UserEntity> getUsersAll() {
        return userEntityRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userEntityRepository.findById(id);
    }

    public void saveUser(UserEntity userEntity) {
        if (userEntity != null) {
            userEntity.setRoles(Collections.singleton(new RoleEntity("USER")));
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
            userEntityRepository.save(userEntity);
        }

    }

    public void deleteUser(UserEntity userEntity) {
        if (userEntity != null) {
            userEntityRepository.delete(userEntity);
        }
    }

    public void deleteUserById(Long id) {
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if (userEntity.isPresent()) {
            userEntityRepository.delete(userEntity.get());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}