package com.example.Shop.services;

import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.repos.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Iterable<UserEntity> getUsersAll(){
        return userEntityRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id){
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if(userEntity.isPresent()){
            return userEntity;
        } else return Optional.empty();

    }
    public void saveUser(UserEntity userEntity){
        if(userEntity!=null){
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
            userEntityRepository.save(userEntity);
        }

    }
    public void deleteUser(UserEntity userEntity){
        if(userEntity!=null){
            userEntityRepository.delete(userEntity);
        }
    }
    public void deleteUserById(Long id){
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        if(userEntity.isPresent()){
            userEntityRepository.delete(userEntity.get());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}