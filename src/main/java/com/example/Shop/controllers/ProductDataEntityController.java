package com.example.Shop.controllers;

import com.example.Shop.db.dto.CategoryRelatedDTO.ProductDataEntityDTO;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.services.ProductDataEntityService;
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
@RequestMapping("api/products/{productId}/info")
@CrossOrigin(origins = {"http://localhost:4200/", "https://summer-practy.herokuapp.com/"}, maxAge = 3600)
public class ProductDataEntityController {
    final private ProductDataEntityService productDataEntityService;
    final private ProductEntityService productEntityService;
    final private ModelMapper modelMapper;

    public ProductDataEntityController(ProductDataEntityService productDataEntityService, ProductEntityService productEntityService, ModelMapper modelMapper) {
        this.productDataEntityService = productDataEntityService;
        this.productEntityService = productEntityService;
        this.modelMapper = modelMapper;
    }

    public ProductDataEntityDTO EntityToDTO(ProductDataEntity productDataEntity) {
        return modelMapper.map(productDataEntity, ProductDataEntityDTO.class);
    }

    public ProductDataEntity DTOToEntity(ProductDataEntityDTO productDataEntityDTO) {
        return modelMapper.map(productDataEntityDTO, ProductDataEntity.class);
    }

    @GetMapping
    List<ProductDataEntityDTO> getProductDataEntities( @PathVariable Long productId) {

            Optional<ProductEntity> productEntity = productEntityService.getProductById(productId);
            if (productEntity.isPresent()) {
                return productEntity.get().getSizesAndColors().stream()
                        .map(this::EntityToDTO)
                        .collect(Collectors.toList());
            }
        return null;
    }

    @GetMapping("/{ID}/")
    ProductDataEntityDTO getProductDataEntity( @PathVariable Long productId, @PathVariable Long ID) {
            Optional<ProductEntity> productEntity = productEntityService.getProductAll().stream()
                    .filter(pe -> pe.getId().equals(productId)).findAny();
            if (productEntity.isPresent()) {
                Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream()
                        .filter(pde -> pde.getId().equals(ID))
                        .findAny();
                if (productDataEntity.isPresent()) {
                    return EntityToDTO(productDataEntity.get());
                }
            }
        return null;
    }


    @PostMapping
    ProductDataEntityDTO postProductDataEntity( @Valid @RequestBody ProductDataEntityDTO productDataEntityDTO, @PathVariable Long productId) {
            Optional<ProductEntity> productEntity = productEntityService.getProductAll().stream()
                    .filter(pe -> pe.getId().equals(productId)).findAny();
            if (productEntity.isPresent()) {
                ProductDataEntity productDataEntity = DTOToEntity(productDataEntityDTO);
                ProductEntity productEntity1 = productEntity.get();
                //productEntity1.getSizesAndColors().add(productDataEntity);
                productDataEntity.setProductEntity(productEntity1);
                productDataEntityService.saveProductData(productDataEntity);
                //productEntityService.saveProduct(productEntity1);
                return productDataEntityDTO;
            }
        return null;
    }

    @PutMapping("/{ID}/")
    ProductDataEntityDTO putProductDataEntity(
                                              @PathVariable Long productId,
                                              @PathVariable Long ID,
                                              @Valid @RequestBody ProductDataEntityDTO productDataEntityDTO) {
            Optional<ProductEntity> productEntity = productEntityService.getProductAll().stream()
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
        return null;
    }

    @DeleteMapping("/{ID}/")
    void deleteProductDataEntity(
                                 @PathVariable Long productId,
                                 @PathVariable Long ID) {
            Optional<ProductEntity> productEntity = productEntityService.getProductAll().stream()
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

