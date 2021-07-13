package com.example.Shop.controllers;


import com.example.Shop.db.dto.ProductEntityDTO;
import com.example.Shop.db.entities.ProductRelatedEntities.CategoryEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.services.CategoryEntityService;
import com.example.Shop.services.ProductEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("category/{CID}/products")
public class ProductEntityController {
    @Autowired
    private ProductEntityService productEntityService;
    @Autowired
    private CategoryEntityService categoryEntityService;

    @Autowired
    ModelMapper modelMapper;

    public ProductEntityDTO EntityToDTO(ProductEntity productEntity){
        return modelMapper.map(productEntity, ProductEntityDTO.class);
    }

    public ProductEntity DTOToEntity(ProductEntityDTO productEntityDTO){
        return modelMapper.map(productEntityDTO, ProductEntity.class);
    }

    public ProductEntityController() { }

    @GetMapping
    List<ProductEntityDTO> getProducts(@PathVariable Long CID){
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            return categoryEntity.get().getProductEntitySet().stream()
                    .map(this::EntityToDTO)
                    .collect(Collectors.toList());
        }
        else return null;
    }

    @GetMapping("/{id}")
    ProductEntityDTO getProduct(@PathVariable Long CID, @PathVariable Long id){
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            Optional<ProductEntity> productEntity = categoryEntity.get().getProductEntitySet().stream().filter(ce -> ce.getId().equals(id)).findAny();
            if(productEntity.isPresent()){
                return EntityToDTO(productEntity.get());
            }
        } return null;
    }

    @PostMapping
    ProductEntityDTO postProduct(@PathVariable Long CID, @RequestBody ProductEntityDTO productEntityDTO){
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            productEntityService.saveProduct(DTOToEntity(productEntityDTO));
            return productEntityDTO;
        } return null;
    }

    @PutMapping("/{id}")
    void putProductEntity(@PathVariable Long id,
                                                   @RequestBody ProductEntityDTO productEntityDTO,
                                                   @PathVariable Long CID) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(CID);
        if(categoryEntity.isPresent()){
            if(categoryEntity.get().getProductEntitySet().stream().anyMatch(ce -> ce.getId().equals(id))){
                productEntityService.putProduct(id, DTOToEntity(productEntityDTO));
            }
        }
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
