package com.example.Shop.components;

import com.example.Shop.entities.CartEntity;
import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.entities.OrderEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.repos.*;
import com.example.Shop.services.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Set;

@Component
class DataLoader {
    private final ProductEntityRepository productEntityRepository;
    private final ProductDataEntityRepository productDataEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final UserDetailsService userDetailsService;
    private final UserAuthorityRepository userAuthorityRepository;
    private final OrderEntityRepository orderEntityRepository;
    private final CartEntityRepository cartEntityRepository;
    public DataLoader(ProductEntityRepository productEntityRepository, ProductDataEntityRepository productDataEntityRepository, CategoryEntityRepository categoryEntityRepository, UserDetailsService userDetailsService, UserAuthorityRepository userAuthorityRepository, OrderEntityRepository orderEntityRepository, CartEntityRepository cartEntityRepository){
        this.productEntityRepository = productEntityRepository;
        this.productDataEntityRepository = productDataEntityRepository;
        this.categoryEntityRepository = categoryEntityRepository;
        this.userDetailsService = userDetailsService;
        this.userAuthorityRepository = userAuthorityRepository;
        this.orderEntityRepository = orderEntityRepository;
        this.cartEntityRepository = cartEntityRepository;
    }
    @PostConstruct
    @Transactional
    private void loadData() {
// херня, не работает, пишет detached entity passed to persist: <любой класс> , как я только не пытался связать все сущности между собой, не получается
//        CategoryEntity categoryEntity = new CategoryEntity("Category name", "Category description");
//        ProductEntity productEntity = new ProductEntity("Product name", "Product description", 245.0);
//        ProductDataEntity productDataEntity = new ProductDataEntity("Color", 12, 12);
//        UserEntity userEntity = new UserEntity("Mikhail", "Kuznetsov", "cyberic-icus", "1234");
//        CartEntity cartEntity = new CartEntity();
//        OrderEntity orderEntity = new OrderEntity();
//
//
//
//        productDataEntity.setProductEntity(productEntity);
//        productEntity.getSizesAndColors().add(productDataEntity);
//        categoryEntity.getProductEntitySet().add(productEntity);
//
//        cartEntity.getProducts().add(productEntity);
//        productEntity.setCartEntity(cartEntity);
//        userEntity.setCartEntity(cartEntity);
//
//        orderEntity.getProductEntitySet().add(productEntity);
//        productEntity.setOrderEntity(orderEntity);
//        userEntity.setOrders(Set.of(orderEntity));
//
//
//        productDataEntityRepository.save(productDataEntity);
//        productEntityRepository.save(productEntity);
//        categoryEntityRepository.save(categoryEntity);
//        orderEntityRepository.save(orderEntity);
//        cartEntityRepository.save(cartEntity);
//        userDetailsService.saveUser(userEntity);


    }
}