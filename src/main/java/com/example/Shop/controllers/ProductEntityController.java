package com.example.Shop.controllers;


import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.repos.ProductEntityRepository;
import com.example.Shop.services.CategoryEntityService;
import com.example.Shop.services.ProductEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("category/{CID}/products")
public class ProductEntityController {
    @Autowired
    private ProductEntityService productEntityService;
    @Autowired
    private CategoryEntityService categoryEntityService;

    public ProductEntityController() { }

    @GetMapping
    Iterable<ProductEntity> getProducts(@PathVariable Long CID){
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            return categoryEntity.get().getProductEntitySet();
        }
        else return null;
    }

    @GetMapping("/{id}")
    Optional<ProductEntity> getProduct(@PathVariable Long CID, @PathVariable Long id){
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            return categoryEntity.get().getProductEntitySet().stream().filter(ce -> ce.getId().equals(id)).findAny();
        } else return Optional.empty();
    }

    @PostMapping
    ProductEntity postProduct(@PathVariable Long CID, @RequestBody ProductEntity product){
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            productEntityService.saveProduct(product); //Нужно здесь сохранять в сет категрии? или спринг сам додуемается это сделать?
            return product;
        } else return null;

    }

    @PutMapping("/{id}")
    ResponseEntity<ProductEntity> putProductEntity(@PathVariable Long id,
                                                   @RequestBody ProductEntity productEntity,
                                                   @PathVariable Long CID) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            if(categoryEntity.get().getProductEntitySet().stream().anyMatch(ce -> ce.getId().equals(id))){
                return productEntityService.putProduct(id, productEntity);
            }
        }
        return null;
    }
    @DeleteMapping("/{id}")
    void deleteProductEntity(@PathVariable Long CID, @PathVariable Long id) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            if(categoryEntity.get().getProductEntitySet().stream().anyMatch(ce -> ce.getId().equals(id))){
                productEntityService.deleteProductById(id);
            }
        }
    }



}
