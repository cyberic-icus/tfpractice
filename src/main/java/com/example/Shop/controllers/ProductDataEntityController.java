package com.example.Shop.controllers;

import com.example.Shop.db.dto.CategoryRelatedDTO.ProductDataEntityDTO;
import com.example.Shop.db.entities.CategoryRelatedEntities.CategoryEntity;
import com.example.Shop.db.entities.CategoryRelatedEntities.ProductDataEntity;
import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
import com.example.Shop.services.CategoryEntityService;
import com.example.Shop.services.ProductDataEntityService;
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
@RequestMapping("category/{categoryId}/products/{productId}/info/")
@CrossOrigin(origins = {"http://localhost:4200/", "https://summer-practy.herokuapp.com/"}, maxAge = 3600)
public class ProductDataEntityController {
    @Autowired
    private ProductDataEntityService productDataEntityService;
    @Autowired
    private ProductEntityService productEntityService;
    @Autowired
    private CategoryEntityService categoryEntityService;

    @Autowired
    ModelMapper modelMapper;

    public ProductDataEntityDTO EntityToDTO(ProductDataEntity productDataEntity){
        return modelMapper.map(productDataEntity, ProductDataEntityDTO.class);
    }

    public ProductDataEntity DTOToEntity(ProductDataEntityDTO productDataEntityDTO){
        return modelMapper.map(productDataEntityDTO, ProductDataEntity.class);
    }

    @GetMapping
    List<ProductDataEntityDTO> getProductDataEntities(@PathVariable Long categoryId, @PathVariable Long productId) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if(categoryEntity.isPresent()){
            Optional<ProductEntity> productEntity = productEntityService.getProductById(productId);
            if(productEntity.isPresent()){
                return productEntity.get().getSizesAndColors().stream()
                        .map(this::EntityToDTO)
                        .collect(Collectors.toList());
            }
        }

        return null;
    }

    @GetMapping("/{ID}/")
    ProductDataEntityDTO getProductDataEntity(@PathVariable Long categoryId, @PathVariable Long productId, @PathVariable Long ID) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            Optional<ProductEntity> productEntity = categoryEntity.get().getCategoryProductEntitySet().stream()
                    .filter(pe -> pe.getId().equals(productId)).findAny();
            if (productEntity.isPresent()) {
                Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream()
                        .filter(pde -> pde.getId().equals(ID))
                        .findAny();
                if (productDataEntity.isPresent()) {
                    return EntityToDTO(productDataEntity.get());
                }
            }
        }
        return null;
    }


    @PostMapping
    ProductDataEntity postProductDataEntity(@PathVariable Long categoryId, @Valid @RequestBody ProductDataEntityDTO productDataEntityDTO, @PathVariable Long productId) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            Optional<ProductEntity> productEntity = categoryEntity.get().getCategoryProductEntitySet().stream()
                    .filter(pe -> pe.getId().equals(productId)).findAny();
            if (productEntity.isPresent()) {
                ProductDataEntity productDataEntity = DTOToEntity(productDataEntityDTO);
                productEntity.get().getSizesAndColors().add(productDataEntity);
                productDataEntityService.saveProductData(productDataEntity);
                return productDataEntity;
            }
        }
        return null;
    }

    @PutMapping("/{ID}/")
    ProductDataEntityDTO putProductDataEntity(@PathVariable Long categoryId,
                              @PathVariable Long productId,
                              @PathVariable Long ID,
                              @Valid @RequestBody ProductDataEntityDTO productDataEntityDTO) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            Optional<ProductEntity> productEntity = categoryEntity.get().getCategoryProductEntitySet().stream()
                    .filter(pe -> pe.getId().equals(productId)).findAny();
            if (productEntity.isPresent()) {
                Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream()
                        .filter(pde -> pde.getId().equals(ID))
                        .findAny();
                ProductDataEntity newProductData = DTOToEntity(productDataEntityDTO);
                if (productDataEntity.isPresent()) {
                    ProductDataEntity oldProductData = productDataEntity.get();
                    oldProductData.setQuantity(newProductData.getQuantity());
                    oldProductData.setColor(newProductData.getColor());
                    oldProductData.setSize(newProductData.getSize());
                    return productDataEntityDTO;
                }
            }
        }
        return null;
    }

    @DeleteMapping("/{ID}/")
    void deleteProductDataEntity(@PathVariable Long categoryId,
                                 @PathVariable Long productId,
                                 @PathVariable Long ID) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(categoryId);
        if (categoryEntity.isPresent()) {
            Optional<ProductEntity> productEntity = categoryEntity.get().getCategoryProductEntitySet().stream()
                    .filter(pe -> pe.getId().equals(productId)).findAny();
            if (productEntity.isPresent()) {
                Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream()
                        .filter(pde -> pde.getId().equals(ID))
                        .findAny();
                if (productDataEntity.isPresent()) {
                    productDataEntityService.deleteProductDataById(ID);
                }
            }
        }
    }

}
