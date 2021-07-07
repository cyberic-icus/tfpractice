package com.example.Shop.components;

import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.repos.ProductDataEntityRepository;
import com.example.Shop.repos.ProductEntityRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
class DataLoader {
    private final ProductEntityRepository productEntityRepository;
    private final ProductDataEntityRepository productDataEntityRepository;
    public DataLoader(ProductEntityRepository productEntityRepository, ProductDataEntityRepository productDataEntityRepository){
        this.productEntityRepository = productEntityRepository;
        this.productDataEntityRepository = productDataEntityRepository;
    }
    @PostConstruct
    @Transactional
    private void loadData() {

        ProductEntity productEntity = new ProductEntity("Test", "Test desc", 245.0);
        ProductDataEntity productDataEntity = new ProductDataEntity("Test Color");

        productEntity.getSizesAndColors().add(productDataEntity);
        productDataEntity.setProductEntity(productEntity);
        productDataEntityRepository.save(productDataEntity);
        productEntityRepository.save(productEntity);

    }
}