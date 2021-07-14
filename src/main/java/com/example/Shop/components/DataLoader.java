package com.example.Shop.components;

import com.example.Shop.db.entities.ProductRelatedEntities.CartEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.CategoryEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.OrderEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserAuthority;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserRole;
import com.example.Shop.db.repos.*;
import com.example.Shop.services.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

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
//        UserEntity userEntity = new UserEntity("Mikhail", "Kuznetsov", "cyberic-icus", "12344", "Test@email.com","+00000000000", "My Dest");
//        CategoryEntity categoryEntity = new CategoryEntity("My category name", "Test description");
//
//        UserAuthority userAuthority = new UserAuthority(UserRole.USER);
//        UserAuthority userAuthority1 = new UserAuthority(UserRole.ADMIN);
//
//
//        userAuthority.getUsers().add(userEntity);
//        userAuthority1.getUsers().add(userEntity);
//
//        userEntity.getRoles().add(userAuthority);
//        userEntity.getRoles().add(userAuthority1);
//
//        CartEntity cartEntity = new CartEntity();
//        OrderEntity orderEntity = new OrderEntity();
//
//        ProductEntity productEntity = new ProductEntity("Product name", "Product description", 245.0, "http://someurl.com/someimage.jpg");
//        ProductDataEntity productDataEntity = new ProductDataEntity("Some Color", 1233, 1332);
//
//        productDataEntity.setProductEntity(productEntity);
//        productEntity.getSizesAndColors().add(productDataEntity);
//
//        orderEntity.getProductEntitySet().add(productEntity);
//        productEntity.setOrderEntity(orderEntity);
//
//        cartEntity.getProducts().add(productEntity);
//        productEntity.setCartEntity(cartEntity);
//
//        categoryEntity.getProductEntitySet().add(productEntity);
//        productEntity.setCategoryEntity(categoryEntity);
//
//        userEntity.setCartEntity(cartEntity);
//        cartEntity.setUserEntity(userEntity);
//
//        userEntity.getOrders().add(orderEntity);
//        orderEntity.setUserEntity(userEntity);
//
//       userDetailsService.saveUser(userEntity);
//       userAuthorityRepository.saveAll(List.of(userAuthority, userAuthority1));


    }
}