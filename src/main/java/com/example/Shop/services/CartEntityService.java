package com.example.Shop.services;

import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.CartEntity;
import com.example.Shop.db.repos.CartEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class CartEntityService {
    final private CartEntityRepository cartEntityRepository;

    public CartEntityService(CartEntityRepository cartEntityRepository) {
        this.cartEntityRepository = cartEntityRepository;
    }

    public void saveProductToCart(CartEntity cartEntity, ProductEntity productEntity) {
        cartEntity.getCartProducts().add(productEntity);
    }

    public void saveCartEntity(CartEntity cartEntity) {
        cartEntityRepository.save(cartEntity);
    }
}
