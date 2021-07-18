package com.example.Shop.controllers;


import com.example.Shop.db.dto.CategoryRelatedDTO.ProductEntityDTO;
import com.example.Shop.db.entities.CategoryRelatedEntities.CategoryEntity;
import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
import com.example.Shop.services.CategoryEntityService;
import com.example.Shop.services.ProductEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("category/{categoryId}/products")
@CrossOrigin(origins = {"http://localhost:4200/", "https://summer-practy.herokuapp.com/"}, maxAge = 3600)
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

    @GetMapping
    List<ProductEntityDTO> getProducts(@PathVariable Long categoryId) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            return categoryEntity.get().getCategoryProductEntitySet().stream()
                    .map(this::EntityToDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/{id}")
    ProductEntityDTO getProduct(@PathVariable Long categoryId, @PathVariable Long id) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            Optional<ProductEntity> productEntity = categoryEntity.get().getCategoryProductEntitySet().stream().
                    filter(ce -> ce.getId().equals(id)).
                    findAny();
            if(productEntity.isPresent()){
                return EntityToDTO(productEntity.get());
            }
        } return null;
    }

    @PostMapping
    ProductEntityDTO postProduct(@PathVariable Long categoryId, @Valid @RequestBody ProductEntityDTO productEntityDTO) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            ProductEntity productEntity = DTOToEntity(productEntityDTO);
            categoryEntity.get().getCategoryProductEntitySet().add(productEntity);
            productEntity.getProductCategories().add(categoryEntity.get());
            productEntityService.saveProduct(productEntity);
            //categoryEntityService.saveCategoryEntity(productCategories.get());
        }
        return null;

    }

    @PutMapping("/{id}")
    ProductEntityDTO putProductEntity(@PathVariable Long id,
                                   @Valid @RequestBody ProductEntityDTO productEntityDTO,
                                   @PathVariable Long categoryId) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            Optional<ProductEntity> productEntity = categoryEntity.get().getCategoryProductEntitySet().stream().
                    filter(ce -> ce.getId().equals(id)).
                    findAny();
            ProductEntity newProduct = DTOToEntity(productEntityDTO);
            if(productEntity.isPresent()){
                ProductEntity oldProduct = productEntity.get();
                oldProduct.setProductName(newProduct.getProductName());
                oldProduct.setProductDescription(newProduct.getProductDescription());
                oldProduct.setPrice(newProduct.getPrice());
                oldProduct.setImageUrl(newProduct.getImageUrl());
                oldProduct.setCreatedDate(newProduct.getCreatedDate());
                return EntityToDTO(productEntity.get());
            } else productEntityService.saveProduct(newProduct);
        } return null;
    }

    @DeleteMapping("/{id}")
    void deleteProductEntity(@PathVariable Long categoryId, @PathVariable Long id) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            if (categoryEntity.get().getCategoryProductEntitySet().stream().anyMatch(ce -> ce.getId().equals(id))) {
                productEntityService.deleteProductById(id);
            }
        }
    }


}