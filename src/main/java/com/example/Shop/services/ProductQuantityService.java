package com.example.Shop.services;

import com.example.Shop.db.entities.OrderRelatedEntites.ProductQuantityEntity;
import com.example.Shop.db.repos.ProductQuantityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductQuantityService {
    final private ProductQuantityRepository productQuantityRepository;

    public ProductQuantityService(ProductQuantityRepository productQuantityRepository) {
        this.productQuantityRepository = productQuantityRepository;
    }

    public List<ProductQuantityEntity> getProductQuantityAll() {
        return (List<ProductQuantityEntity>) productQuantityRepository.findAll();
    }

    public Optional<ProductQuantityEntity> getProductQuantityById(Long id) {
        return productQuantityRepository.findById(id);
    }

    public void saveProductQuantity(ProductQuantityEntity productQuantityEntity) {
        if (productQuantityEntity != null) {
            productQuantityRepository.save(productQuantityEntity);
        }
    }

    public void saveProductQuantityAll(List<ProductQuantityEntity> productQuantityEntity) {
        if (productQuantityEntity != null) {
            productQuantityRepository.saveAll(productQuantityEntity);
        }
    }

    public void deleteProductQuantity(Long id) {
        Optional<ProductQuantityEntity> productQuantityEntity = productQuantityRepository.findById(id);
        if (productQuantityEntity.isPresent()) {
            productQuantityRepository.deleteById(id);
        }
    }
}
