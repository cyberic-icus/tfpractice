package com.example.Shop.services;

import com.example.Shop.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.repos.ProductDataEntityRepository;
import com.example.Shop.repos.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDataEntityService {
    @Autowired
    private ProductDataEntityRepository productDataEntityRepository;
    @Autowired
    private ProductEntityRepository productEntityRepository;


    public void saveProductData(Long PID, ProductDataEntity productDataEntity){
        Optional<ProductEntity> productEntity = productEntityRepository.findById(PID);
        if((productDataEntity!=null)&&(productEntity.isPresent())){
            productDataEntity.setProductEntity(productEntity.get());
            productEntity.get().getSizesAndColors().add(productDataEntity);
            productEntityRepository.save(productEntity.get());
            //productDataEntityRepository.save(productDataEntity);
        }
    }

    public Optional<ProductDataEntity> getProductById(Long id){
        return productDataEntityRepository.findById(id);
    }

    public Iterable<ProductDataEntity> getProductDataAll(){
        return productDataEntityRepository.findAll();
    }

    public void deleteProductDataById(Long PID, Long id){
        Optional<ProductEntity> productEntity = productEntityRepository.findById(PID);
        Optional<ProductDataEntity> productDataEntity = productDataEntityRepository.findById(id);
        if((productEntity.isPresent())&&productDataEntity.isPresent()){
            productEntity.get().getSizesAndColors().remove(productDataEntity.get());
            productDataEntityRepository.deleteById(id);
            productEntityRepository.save(productEntity.get());

        }
    }

    public boolean productDataExistsById(Long id){
        return productDataEntityRepository.existsById(id);
    }

    public ResponseEntity<ProductDataEntity> putProductData(Long id, ProductDataEntity productDataEntity){
        return
                (productDataEntityRepository.existsById(id))
                        ? new ResponseEntity<>(productDataEntityRepository.save(productDataEntity), HttpStatus.CREATED)
                        : new ResponseEntity<>(productDataEntityRepository.save(productDataEntity),
                        HttpStatus.OK);
    }
}