package com.example.Shop.controllers;

import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserEntityController {
    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping
    public Iterable<UserEntity> getUserEntityAll(){
        return userDetailsService.getUsersAll();
    }

    @GetMapping("/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Long id){
        Optional<UserEntity> userEntity = userDetailsService.getUserById(id);
        if(userEntity.isPresent()){
            return userEntity;
        } else return Optional.empty();
    }

    @PostMapping
    public UserEntity postUser(@RequestBody UserEntity userEntity){
        if(userEntity!=null){
            userDetailsService.saveUser(userEntity);
            return userEntity;
        } else return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        Optional<UserEntity> userEntity = userDetailsService.getUserById(id);
        if(userEntity.isPresent()){
            userDetailsService.deleteUserById(id);
        }
    }

    @PutMapping("/{id}")
    public void putUserById(@PathVariable Long id, @RequestBody UserEntity userEntity1){
        Optional<UserEntity> userEntity = userDetailsService.getUserById(id);
        if(userEntity.isPresent()){
            userDetailsService.putProduct(id, userEntity1);
        }
    }

}
