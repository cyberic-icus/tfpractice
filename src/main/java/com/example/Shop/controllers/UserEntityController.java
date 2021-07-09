package com.example.Shop.controllers;

import com.example.Shop.entities.CartEntity;
import com.example.Shop.entities.OrderEntity;
import com.example.Shop.entities.UserRelatedEntities.UserAuthority;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.services.UserAuthorityService;
import com.example.Shop.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserEntityController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserAuthorityService userAuthorityService;

    @GetMapping
    public Iterable<UserEntity> getUserEntityAll() {
        return userDetailsService.getUsersAll();
    }

    @GetMapping("/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(id);
        if (userEntity.isPresent()) {
            return userEntity;
        } else return Optional.empty();
    }

    @PostMapping
    public UserEntity postUser(@RequestBody UserEntity userEntity) {
        if (userEntity != null) {
            userDetailsService.saveUser(userEntity);
            return userEntity;
        } else return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(id);
        if (userEntity.isPresent()) {
            userDetailsService.deleteUserById(id);
        }
    }

    @PutMapping("/{id}")
    public void putUserById(@PathVariable Long id, @RequestBody UserEntity userEntity1) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(id);
        if (userEntity.isPresent()) {
            userDetailsService.putProduct(id, userEntity1);
        }
    }

    @GetMapping("/{uid}/auths")
    public Iterable<UserAuthority> getUserAuthorityAll(@PathVariable Long uid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            return (Iterable<UserAuthority>) userEntity.get().getAuthorities();
        } else return null;

    }

    @GetMapping("/{uid}/orders")
    public Iterable<OrderEntity> getUserOrdersAll(@PathVariable Long uid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            return userEntity.get().getOrders();
        } else return null;
    }

    @GetMapping("/{uid}/cart")
    public CartEntity getUserCart(@PathVariable Long uid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            return userEntity.get().getCartEntity();
        } else return null;
    }

    @GetMapping("/{uid}/auths/{aid}/")
    public UserAuthority getUserAuthority(@PathVariable Long uid, @PathVariable Long aid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        Optional<UserAuthority> userAuthority = userAuthorityService.getUserAuthorityById(aid);
        if ((userEntity.isPresent()) && userAuthority.isPresent()) {
            //return userEntity.get().getAuthorities().stream().anyMatch(auth -> userAuthority.get().equals(auth));
        }  return null;

    }

}
