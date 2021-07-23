package com.example.Shop.services;

import com.example.Shop.db.dto.OrderEntityDTO.OrderEntityDTO;
import com.example.Shop.db.dto.OrderEntityDTO.ProductQuantityDTO;
import com.example.Shop.db.entities.OrderRelatedEntites.OrderEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.db.repos.OrderEntityRepository;
import com.example.Shop.db.repos.ProductDataEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderEntityService {
    final private OrderEntityRepository orderEntityRepository;
    final private ProductDataEntityService productDataEntityService;
    final private UserEntityService userEntityService;
    final private ProductDataEntityRepository productDataEntityRepository;

    public OrderEntityService(OrderEntityRepository orderEntityRepository, ProductDataEntityService productDataEntityService, UserEntityService userEntityService, ProductDataEntityRepository productDataEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
        this.productDataEntityService = productDataEntityService;
        this.userEntityService = userEntityService;
        this.productDataEntityRepository = productDataEntityRepository;
    }

    public void saveOrder(OrderEntity orderEntity) {
        if (orderEntity != null) {
            orderEntityRepository.save(orderEntity);
        }
    }

    public List<OrderEntity> getOrdersAll() {
        return (List<OrderEntity>) orderEntityRepository.findAll();
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderEntityRepository.findById(id);
    }

    public void deleteOrder(Long id) {
        Optional<OrderEntity> orderEntity = orderEntityRepository.findById(id);
        if (orderEntity.isPresent()) {
            orderEntityRepository.deleteById(id);
        }
    }

    public List<ProductQuantityDTO> checkOutQuan(OrderEntityDTO orderEntityDTO) {
        List<ProductQuantityDTO> productQuantityDTOList = orderEntityDTO.getProductList().stream()
                .filter(pq -> productDataEntityRepository.existsById(pq.getProductDataId()))
                .filter(
                        pq -> {
                            ProductDataEntity productDataEntity = productDataEntityService.getProductById(pq.getProductDataId()).get();
                            return productDataEntity.getQuantity() - pq.getQuantity() >= 0L;
                        }
                )
                .collect(Collectors.toList());

        for (ProductQuantityDTO productq : productQuantityDTOList) {
            ProductDataEntity productDataEntity = productDataEntityService.getProductById(productq.getProductDataId()).get();
            productDataEntity.setQuantity(
                    productDataEntity.getQuantity() - productq.getQuantity()
            );
            productDataEntityService.saveProductData(productDataEntity);


        }
        return productQuantityDTOList;
    }
}