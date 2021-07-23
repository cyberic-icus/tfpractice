package com.example.Shop.controllers;


import com.example.Shop.db.dto.ProductRelatedDTO.ProductEntityDTO;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.services.ProductEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("api/products")
@CrossOrigin(origins = {"http://localhost:4200/", "https://summer-practy.herokuapp.com/"}, maxAge = 3600)
public class ProductEntityController {
    final private ProductEntityService productEntityService;
    final private ModelMapper modelMapper;

    public ProductEntityController(ProductEntityService productEntityService, ModelMapper modelMapper) {
        this.productEntityService = productEntityService;
        this.modelMapper = modelMapper;
    }

    public ProductEntityDTO EntityToDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductEntityDTO.class);
    }

    public ProductEntity DTOToEntity(ProductEntityDTO productEntityDTO) {
        return modelMapper.map(productEntityDTO, ProductEntity.class);
    }

    @GetMapping
    List<ProductEntityDTO> getProduct() {
        return productEntityService.getProductAll().stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    ProductEntityDTO getProduct(@PathVariable Long id) {
        Optional<ProductEntity> productEntity = productEntityService.getProductAll().stream().
                filter(ce -> ce.getId().equals(id)).
                findAny();
        if (productEntity.isPresent()) {
            return EntityToDTO(productEntity.get());
        }
        return null;
    }

    @PostMapping
    void postProduct(@Valid @RequestBody ProductEntityDTO productEntityDTO) {
        ProductEntity productEntity = DTOToEntity(productEntityDTO);
        productEntityService.getProductAll().add(productEntity);
        productEntityService.saveProduct(productEntity);
    }

    @PutMapping("/{id}")
    ProductEntityDTO putProductEntity(@PathVariable Long id,
                                      @Valid @RequestBody ProductEntityDTO productEntityDTO) {
        Optional<ProductEntity> productEntity = productEntityService.getProductAll().stream().
                filter(ce -> ce.getId().equals(id)).
                findAny();
        ProductEntity newProduct = DTOToEntity(productEntityDTO);
        if (productEntity.isPresent()) {
            ProductEntity oldProduct = productEntity.get();
            oldProduct.setProductName(newProduct.getProductName());
            oldProduct.setProductDescription(newProduct.getProductDescription());
            oldProduct.setPrice(newProduct.getPrice());
            oldProduct.setImageUrl(newProduct.getImageUrl());
            oldProduct.setCreatedDate(newProduct.getCreatedDate());
            return EntityToDTO(productEntity.get());
        } else productEntityService.saveProduct(newProduct);
        return null;
    }

    @DeleteMapping("/{id}")
    void deleteProductEntity(@PathVariable Long id) {
        if (productEntityService.getProductAll().stream().anyMatch(ce -> ce.getId().equals(id))) {
            productEntityService.deleteProductById(id);
        }
    }
}


