package com.example.Shop.controllers;

import com.example.Shop.db.dto.ProductDataEntityDTO;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.services.ProductDataEntityService;
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
@RequestMapping("category/{CID}/products/{ProductID}/avail/")
public class ProductDataEntityController {
    @Autowired
    private ProductDataEntityService productDataEntityService;
    @Autowired
    private ProductEntityService productEntityService;

    @Autowired
    ModelMapper modelMapper;

    public ProductDataEntityDTO EntityToDTO(ProductDataEntity productDataEntity){
        return modelMapper.map(productDataEntity, ProductDataEntityDTO.class);
    }

    public ProductDataEntity DTOToEntity(ProductDataEntityDTO productDataEntityDTO){
        return modelMapper.map(productDataEntityDTO, ProductDataEntity.class);
    }

    @GetMapping
    List<ProductDataEntityDTO> getProductDataEntities(@PathVariable Long ProductID){
        Optional<ProductEntity> productEntity = productEntityService.getProductById(ProductID);
        if(productEntity.isPresent()){
            return productEntity.get().getSizesAndColors().stream()
                    .map(this::EntityToDTO)
                    .collect(Collectors.toList());
        } else return List.of();
    }

    @GetMapping("/{ID}/")
    ProductDataEntityDTO getProductDataEntities(@PathVariable Long ProductID, @PathVariable Long ID){
        Optional<ProductEntity> productEntity = productEntityService.getProductById(ProductID);
        if(productEntity.isPresent()){
            return EntityToDTO(productDataEntityService.getProductById(ID).get());
        } else return null;
    }


    @PostMapping
    ProductDataEntityDTO postProductDataEntity(@RequestBody ProductDataEntityDTO productDataEntityDTO, @PathVariable Long ProductID){
        productDataEntityService.saveProductData(ProductID, DTOToEntity(productDataEntityDTO));
        return productDataEntityDTO;
    }

    @PutMapping("/{ID}/")
    void putProductDataEntity(@PathVariable Long ID, @RequestBody ProductDataEntityDTO productDataEntityDTO){
        productDataEntityService.putProductData(ID,DTOToEntity(productDataEntityDTO));
    }

    @DeleteMapping("/{ID}/")
    void deleteProductDataEntity(@PathVariable Long ProductID, @PathVariable Long ID){
        productDataEntityService.deleteProductDataById(ProductID, ID);
    }

}
