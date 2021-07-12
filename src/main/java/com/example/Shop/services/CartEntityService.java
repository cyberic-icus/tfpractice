package com.example.Shop.services;

import com.example.Shop.db.entities.ProductRelatedEntities.CartEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.db.repos.CartEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartEntityService {
    @Autowired
    CartEntityRepository cartEntityRepository;

    public void saveProductToCart(CartEntity cartEntity, ProductEntity productEntity){
        cartEntity.getProducts().add(productEntity);
    }
    public void saveCartEntity(CartEntity cartEntity){
        cartEntityRepository.save(cartEntity);
    }
}
