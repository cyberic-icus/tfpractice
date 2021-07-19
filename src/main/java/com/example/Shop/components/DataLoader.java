package com.example.Shop.components;

import com.example.Shop.db.repos.*;
import com.example.Shop.services.UserEntityService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
class DataLoader {
    private final ProductEntityRepository productEntityRepository;
    private final ProductDataEntityRepository productDataEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final UserEntityService userEntityService;
    private final OrderEntityRepository orderEntityRepository;
    private final CartEntityRepository cartEntityRepository;

    public DataLoader(ProductEntityRepository productEntityRepository, ProductDataEntityRepository productDataEntityRepository, CategoryEntityRepository categoryEntityRepository, UserEntityService userEntityService, OrderEntityRepository orderEntityRepository, CartEntityRepository cartEntityRepository) {
        this.productEntityRepository = productEntityRepository;
        this.productDataEntityRepository = productDataEntityRepository;
        this.categoryEntityRepository = categoryEntityRepository;
        this.userEntityService = userEntityService;
        this.orderEntityRepository = orderEntityRepository;
        this.cartEntityRepository = cartEntityRepository;
    }

    @PostConstruct
    @Transactional
    private void loadData() {
//        UserEntity cartUserEntity = new UserEntity("Mikhail", "Kuznetsov", "cyberic-icus", "12344", "Test@email.com","+00000000000", "My Dest");
//        CategoryEntity productCategories = new CategoryEntity("My category categoryName", "Test categoryDescription");
//
//        UserAuthority userAuthority = new UserAuthority(UserRole.USER);
//        UserAuthority userAuthority1 = new UserAuthority(UserRole.ADMIN);
//
//
//        userAuthority.getUsers().add(cartUserEntity);
//        userAuthority1.getUsers().add(cartUserEntity);
//
//        cartUserEntity.getRoles().add(userAuthority);
//        cartUserEntity.getRoles().add(userAuthority1);
//
//        CartEntity productCarts = new CartEntity();
//        OrderEntity productOrders = new OrderEntity();
//
//        ProductEntity productEntity = new ProductEntity("Product categoryName", "Product categoryDescription", 245.0, "http://someurl.com/someimage.jpg");
//        ProductDataEntity productDataEntity = new ProductDataEntity("Some Color", 1233, 1332);
//
//        productDataEntity.setProductEntity(productEntity);
//        productEntity.getSizesAndColors().add(productDataEntity);
//
//        productOrders.getCategoryProductEntitySet().add(productEntity);
//        productEntity.setProductOrders(productOrders);
//
//        productCarts.getCartProducts().add(productEntity);
//        productEntity.setProductCarts(productCarts);
//
//        productCategories.getCategoryProductEntitySet().add(productEntity);
//        productEntity.setProductCategories(productCategories);
//
//        cartUserEntity.setProductCarts(productCarts);
//        productCarts.setCartUserEntity(cartUserEntity);
//
//        cartUserEntity.getOrders().add(productOrders);
//        productOrders.setCartUserEntity(cartUserEntity);
//
//       userEntityService.saveUser(cartUserEntity);
//       userAuthorityRepository.saveAll(List.of(userAuthority, userAuthority1));


    }
}