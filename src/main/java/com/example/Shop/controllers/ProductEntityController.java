package com.example.Shop.controllers;


import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.repos.ProductEntityRepository;
import com.example.Shop.services.ProductEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductEntityController {
    @Autowired
    private ProductEntityService productEntityService;


    public ProductEntityController() { }

    @GetMapping
    Iterable<ProductEntity> getProducts(){
        return productEntityService.getProductAll();
    }

    @GetMapping("/{id}")
    Optional<ProductEntity> getProduct(@PathVariable Long id){
        return productEntityService.getProductById(id);
    }

    @PostMapping
    ProductEntity postProduct(@RequestBody ProductEntity product){
        productEntityService.saveProduct(product);
        return product;
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductEntity> putProductEntity(@PathVariable Long id,
                                                   @RequestBody ProductEntity productEntity) {
        return productEntityService.putProduct(id, productEntity);
    }
    @DeleteMapping("/{id}")
    void deleteProductEntity(@PathVariable Long id) {
        productEntityService.deleteProductById(id);
    }



}
