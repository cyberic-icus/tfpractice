package com.example.Shop.controllers;

import com.example.Shop.entities.CartEntity;
import com.example.Shop.entities.OrderEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.entities.UserRelatedEntities.UserAuthority;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.repos.OrderEntityRepository;
import com.example.Shop.services.ProductDataEntityService;
import com.example.Shop.services.ProductEntityService;
import com.example.Shop.services.UserAuthorityService;
import com.example.Shop.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserEntityController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserAuthorityService userAuthorityService;

    @Autowired
    OrderEntityRepository orderEntityRepository;

    @Autowired
    ProductEntityService productEntityService;

    @Autowired
    ProductDataEntityService productDataEntityService;

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

    @PostMapping("/{uid}/orders")
    public OrderEntity postUserOrder(@PathVariable Long uid, @RequestBody OrderEntity orderEntity) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            userEntity.get().getOrders().add(orderEntity);
            orderEntity.setUserEntity(userEntity.get());
            orderEntityRepository.save(orderEntity);
            return orderEntity;
        }
        return null;
    }

    @DeleteMapping("/{uid}/orders/{oid}")
    public void deleteUserOrder(@PathVariable Long uid, @PathVariable Long oid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = orderEntityRepository.findById(oid);
            if (orderEntity.isPresent()) {
                if (userEntity.get().getOrders().contains(orderEntity.get())) {
                    orderEntityRepository.delete(orderEntity.get());
                }
            }
        }
    }

    @PutMapping("/{uid}/orders/{oid}")
    public void putUserOrder(){}

    @GetMapping("/{uid}/orders/{oid}")
    public Optional<OrderEntity> getUserOrder(@PathVariable Long uid, @PathVariable Long oid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            //System.out.println(userEntity.get().getOrders().toString());
            return userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();

        }
        return null;
    }

    @GetMapping("/{uid}/orders/{oid}/products")
    public Iterable<ProductEntity> getUserOrdersProducts(@PathVariable Long uid, @PathVariable Long oid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                return orderEntity.get().getProductEntitySet();
            }

        }
        return null;
    }
    @PostMapping("/{uid}/orders/{oid}/products")
    public ProductEntity postProductToOrder(@PathVariable Long uid, @PathVariable Long oid, @RequestBody ProductEntity productEntity) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                orderEntity.get().getProductEntitySet().add(productEntity);
                productEntity.setOrderEntity(orderEntity.get());
                productEntityService.saveProduct(productEntity);
                return productEntity;
            }

        }
        return null;
    }


    @GetMapping("/{uid}/orders/{oid}/products/{pid}")
    public Optional<ProductEntity> getUserOrdersProduct(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                return orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
            }

        }
        return null;
    }

    @DeleteMapping("/{uid}/orders/{oid}/products/{pid}")
    public void deleteUserOrderProduct(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                Optional<ProductEntity> productEntity = orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
                if(productEntity.isPresent()){
                    productEntityService.deleteProductById(pid);
                }
            }

        }
    }




    @GetMapping("/{uid}/orders/{oid}/products/{pid}/info")
    public Iterable<ProductDataEntity> getUserOrdersProductsDataEntities(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                Optional<ProductEntity> productEntity = orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
                if (productEntity.isPresent()) {
                    return productEntity.get().getSizesAndColors();
                }

            }

        }
        return null;
    }

    @PostMapping("/{uid}/orders/{oid}/products/{pid}/info")
    public ProductDataEntity getUserOrdersProductsDataEntities(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid, @RequestBody ProductDataEntity productDataEntity) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                Optional<ProductEntity> productEntity = orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
                if (productEntity.isPresent()) {
                    productDataEntityService.saveProductData(pid, productDataEntity);
                }

            }

        }
        return null;
    }

    @GetMapping("/{uid}/orders/{oid}/products/{pid}/info/{prid}")
    public Optional<ProductDataEntity> getUserOrdersProductsDataEntity(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid, @PathVariable Long prid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                Optional<ProductEntity> productEntity = orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
                if (productEntity.isPresent()) {
                    // Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream().filter(pde -> pde.getId().equals(prid)).findAny();
                    return productEntity.get().getSizesAndColors().stream().filter(pde -> pde.getId().equals(prid)).findAny();
                }

            }

        }
        return null;
    }
    @DeleteMapping("/{uid}/orders/{oid}/products/{pid}/info/{prid}")
    public void deleteUserOrderProductData(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid, @PathVariable Long prid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(oid)).findAny();
            if (orderEntity.isPresent()) {
                Optional<ProductEntity> productEntity = orderEntity.get().getProductEntitySet().stream().filter(pe -> pe.getId().equals(pid)).findAny();
                if (productEntity.isPresent()) {
                    Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream().filter(pde -> pde.getId().equals(prid)).findAny();
                    if(productDataEntity.isPresent()){
                        productDataEntityService.deleteProductDataById(pid, prid);
                    }
                }

            }

        }
    }

    @PutMapping("/{uid}/orders/{oid}/products/{pid}/info/{prid}")
    public void putUserProductData(@PathVariable Long uid, @PathVariable Long oid, @PathVariable Long pid, @PathVariable Long prid, @RequestBody ProductDataEntity productDataEntity)
    {}


    @GetMapping("/{uid}/cart")
    public CartEntity getUserCart(@PathVariable Long uid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            return userEntity.get().getCartEntity();
        }
        return null;
    }

    @PostMapping("/{uid}/cart")
    public ProductEntity getUserCart(@PathVariable Long uid, @RequestBody ProductEntity productEntity) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            userEntity.get().getCartEntity().getProducts().add(productEntity);
            productEntity.setCartEntity(userEntity.get().getCartEntity());
            userDetailsService.saveUser(userEntity.get());
        }
        return null;
    }

    @PutMapping("/{uid}/cart/{pid}")
    public ProductEntity putUserCartProduct(@PathVariable Long uid, @PathVariable Long pid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            userDetailsService.saveUser(userEntity.get());
        }
        return null;
    }

    @DeleteMapping("/{uid}/cart/{pid}")
    public void deleteUserCartProdcut(@PathVariable Long uid, @PathVariable Long pid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        if (userEntity.isPresent()) {
            Optional<ProductEntity> productEntity = userEntity.get().getCartEntity().getProducts().stream().filter(pe -> pe.getId().equals(pid)).findAny();
            if(productEntity.isPresent()){
                productEntityService.deleteProductById(pid);
            }
            userDetailsService.saveUser(userEntity.get());
        }
    }



    @GetMapping("/{uid}/auths/{aid}/")
    public UserAuthority getUserAuthority(@PathVariable Long uid, @PathVariable Long aid) {
        Optional<UserEntity> userEntity = userDetailsService.getUserById(uid);
        Optional<UserAuthority> userAuthority = userAuthorityService.getUserAuthorityById(aid);
        if ((userEntity.isPresent()) && userAuthority.isPresent()) {
            Iterable<UserAuthority> userAuthorities = (Iterable<UserAuthority>) userEntity.get().getAuthorities();
            Optional<UserAuthority> userAuthority1 = userAuthority.stream().filter(auth -> userAuthority.get().equals(auth)).findAny();
            if (userAuthority1.isPresent()) {
                return userAuthority1.get();
            }

        }
        return null;

    }

    //@GetMapping("/{uid}/")

}
