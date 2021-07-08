package com.example.Shop.components;

import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.repos.CategoryEntityRepository;
import com.example.Shop.repos.ProductDataEntityRepository;
import com.example.Shop.repos.ProductEntityRepository;
import com.example.Shop.services.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
class DataLoader {
    private final ProductEntityRepository productEntityRepository;
    private final ProductDataEntityRepository productDataEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final UserDetailsService userDetailsService;
    public DataLoader(ProductEntityRepository productEntityRepository, ProductDataEntityRepository productDataEntityRepository, CategoryEntityRepository categoryEntityRepository, UserDetailsService userDetailsService){
        this.productEntityRepository = productEntityRepository;
        this.productDataEntityRepository = productDataEntityRepository;
        this.categoryEntityRepository = categoryEntityRepository;
        this.userDetailsService = userDetailsService;
    }
    @PostConstruct
    @Transactional
    private void loadData() {

        CategoryEntity categoryEntity = new CategoryEntity("Category name", "Category description");
        ProductEntity productEntity = new ProductEntity("Product name", "Product description", 245.0);
        ProductDataEntity productDataEntity = new ProductDataEntity("Color", 12, 12);

        UserEntity userEntity = new UserEntity("Mikhail", "Kuznetsov", "cyberic-icus", "1234");
        productDataEntity.setProductEntity(productEntity);
        productEntity.getSizesAndColors().add(productDataEntity);
        categoryEntity.getProductEntitySet().add(productEntity);

        userDetailsService.saveUser(userEntity);
//        productDataEntityRepository.save(productDataEntity);
//        productEntityRepository.save(productEntity);
        categoryEntityRepository.save(categoryEntity);


    }
}