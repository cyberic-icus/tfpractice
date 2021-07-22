package com.example.Shop.services;

import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.db.repos.ProductEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductEntityService {
    private final ProductEntityRepository productEntityRepository;

    public ProductEntityService(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }

    public void saveProduct(ProductEntity productEntity) {
        if (productEntity != null) {
            productEntityRepository.save(productEntity);
        }
    }

    public Optional<ProductEntity> getProductById(Long id) {
        return productEntityRepository.findById(id);
    }

    public List<ProductEntity> getProductAll() {
        return (List<ProductEntity>) productEntityRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productEntityRepository.deleteById(id);
    }

    public boolean productExistsById(Long id) {
        return productEntityRepository.existsById(id);
    }

    public ResponseEntity<ProductEntity> putProduct(Long id, ProductEntity productEntity) {
        return
                (productEntityRepository.existsById(id))
                        ? new ResponseEntity<>(productEntityRepository.save(productEntity), HttpStatus.CREATED)
                        : new ResponseEntity<>(productEntityRepository.save(productEntity),
                        HttpStatus.OK);
    }
}
