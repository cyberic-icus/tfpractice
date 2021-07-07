package com.example.Shop.controllers;

import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.services.ProductDataEntityService;
import com.example.Shop.services.ProductEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/{ProductID}/availability/")
public class ProductDataEntityController {
    @Autowired
    private ProductDataEntityService productDataEntityService;
    @Autowired
    private ProductEntityService productEntityService;

    @GetMapping
    Iterable<ProductDataEntity> getProductDataEntities(@PathVariable Long ProductID){
        Optional<ProductEntity> productEntity = productEntityService.getProductById(ProductID);
        if(productEntity.isPresent()){
            return productEntity.get().getSizesAndColors();
        } else return List.of();
    }

    @GetMapping("/{ID}/")
    Optional<ProductDataEntity> getProductDataEntities(@PathVariable Long ProductID, @PathVariable Long ID){
        Optional<ProductEntity> productEntity = productEntityService.getProductById(ProductID);
        if(productEntity.isPresent()){
            return productDataEntityService.getProductById(ID);
        } else return Optional.empty();
    }


    @PostMapping
    ProductDataEntity postProductDataEntity(@RequestBody ProductDataEntity productDataEntity, @PathVariable Long ProductID){
        Optional<ProductEntity> productEntity = productEntityService.getProductById(ProductID);
        productEntity.ifPresent(entity -> entity.getSizesAndColors().add(productDataEntity));
        return productDataEntity;
    }

    @PutMapping("/{ID}/")
    ResponseEntity<ProductDataEntity> putProductDataEntity(@PathVariable Long ID,
                                                           @RequestBody ProductDataEntity productDataEntity){
        return productDataEntityService.putProductData(ID,productDataEntity);
    }

    @DeleteMapping("/{ID}/")
    void deleteProductDataEntity(@PathVariable Long ProductID, @PathVariable Long ID){
        productDataEntityService.deleteProductDataById(ProductID, ID);
    }

}
