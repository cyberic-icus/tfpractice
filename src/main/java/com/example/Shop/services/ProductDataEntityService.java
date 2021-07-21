package com.example.Shop.services;

import com.example.Shop.db.entities.CategoryRelatedEntities.ProductDataEntity;
import com.example.Shop.db.repos.ProductDataEntityRepository;
import com.example.Shop.db.repos.ProductEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDataEntityService {

    private final ProductDataEntityRepository productDataEntityRepository;
    private final ProductEntityRepository productEntityRepository;

    public ProductDataEntityService(ProductDataEntityRepository productDataEntityRepository, ProductEntityRepository productEntityRepository) {
        this.productDataEntityRepository = productDataEntityRepository;
        this.productEntityRepository = productEntityRepository;
    }

    public void saveProductData(ProductDataEntity productDataEntity) {
        if (productDataEntity != null) {
            productDataEntityRepository.save(productDataEntity);
        }
    }

    public Optional<ProductDataEntity> getProductById(Long id) {
        return productDataEntityRepository.findById(id);
    }

    public Iterable<ProductDataEntity> getProductDataAll() {
        return productDataEntityRepository.findAll();
    }

    public void deleteProductDataById(Long id) {
        Optional<ProductDataEntity> productDataEntity = productDataEntityRepository.findById(id);
        if (productDataEntity.isPresent()) {
            productDataEntityRepository.deleteById(id);


        }
    }

    public boolean productDataExistsById(Long id) {
        return productDataEntityRepository.existsById(id);
    }

    public ResponseEntity<ProductDataEntity> putProductData(Long id, ProductDataEntity productDataEntity) {
        return
                (productDataEntityRepository.existsById(id))
                        ? new ResponseEntity<>(productDataEntityRepository.save(productDataEntity), HttpStatus.CREATED)
                        : new ResponseEntity<>(productDataEntityRepository.save(productDataEntity),
                        HttpStatus.OK);
    }
}