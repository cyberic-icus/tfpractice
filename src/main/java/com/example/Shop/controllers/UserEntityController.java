package com.example.Shop.controllers;

import com.example.Shop.entities.CartEntity;
import com.example.Shop.entities.OrderEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
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

    @GetMapping("/{uid}/orders/{oid}")
    public Optional<OrderEntity> getUserOrder(@PathVariable Long uid, @PathVariable Long oid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            //System.out.println(userEntity.get().getOrders().toString());
            return userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();

        } return null;
    }

    @GetMapping("/{uid}/orders/{oid}/products")
    public Iterable<ProductEntity> getUserOrdersProducts(@PathVariable Long uid, @PathVariable Long oid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if(orderEntity.isPresent()){
                return orderEntity.get().getProductEntitySet();
            }

        } return null;
    }

    @GetMapping("/{uid}/orders/{oid}/products/{pid}")
    public Optional<ProductEntity> getUserOrdersProduct(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if(orderEntity.isPresent()){
                return orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
            }

        } return null;
    }

    @GetMapping("/{uid}/orders/{oid}/products/{pid}/info")
    public Iterable<ProductDataEntity> getUserOrdersProductsDataEntities(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if(orderEntity.isPresent()){
                Optional<ProductEntity> productEntity = orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
                if(productEntity.isPresent()){
                    return productEntity.get().getSizesAndColors();
                }

            }

        } return null;
    }

    @GetMapping("/{uid}/orders/{oid}/products/{pid}/info/{prid}")
    public Optional<ProductDataEntity> getUserOrdersProductsDataEntity(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid, @PathVariable Long prid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if(orderEntity.isPresent()){
                Optional<ProductEntity> productEntity = orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
                if(productEntity.isPresent()){
                    // Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream().filter(pde -> pde.getId().equals(prid)).findAny();
                    return productEntity.get().getSizesAndColors().stream().filter(pde -> pde.getId().equals(prid)).findAny();
                }

            }

        } return null;
    }




    @GetMapping("/{uid}/cart")
    public CartEntity getUserCart(@PathVariable Long uid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            return userEntity.get().getCartEntity();
        } return null;
    }

    @GetMapping("/{uid}/auths/{aid}/")
    public UserAuthority getUserAuthority(@PathVariable Long uid, @PathVariable Long aid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        Optional<UserAuthority> userAuthority = userAuthorityService.getUserAuthorityById(aid);
        if ((userEntity.isPresent()) && userAuthority.isPresent()) {
            Iterable<UserAuthority> userAuthorities = (Iterable<UserAuthority>) userEntity.get().getAuthorities();
            Optional<UserAuthority> userAuthority1 = userAuthority.stream().filter(auth -> userAuthority.get().equals(auth)).findAny();
            if(userAuthority1.isPresent()){
                return userAuthority1.get();
            }

        }  return null;

    }

    //@GetMapping("/{uid}/")

}
