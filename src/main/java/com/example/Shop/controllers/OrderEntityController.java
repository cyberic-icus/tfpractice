package com.example.Shop.controllers;


import com.example.Shop.db.dto.OrderEntityDTO.OrderEntityDTO;
import com.example.Shop.db.dto.OrderEntityDTO.ProductQuantityDTO;
import com.example.Shop.db.dto.UserRelatedDTO.UserEntityDTO;
import com.example.Shop.db.entities.OrderRelatedEntites.OrderEntity;
import com.example.Shop.db.entities.OrderRelatedEntites.ProductQuantityEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.db.repos.ProductDataEntityRepository;
import com.example.Shop.services.OrderEntityService;
import com.example.Shop.services.ProductQuantityService;
import com.example.Shop.services.UserEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Api
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"http://localhost:4200/", "https://summer-practy.herokuapp.com/"}, maxAge = 3600)
public class OrderEntityController {

    final private OrderEntityService orderEntityService;
    final private ModelMapper modelMapper;
    final private UserEntityService userEntityService;
    final private ProductQuantityService productQuantityService;
    final private ProductDataEntityRepository productDataEntityRepository;

    public OrderEntityController(OrderEntityService orderEntityService, ModelMapper modelMapper, UserEntityService userEntityService, ProductQuantityService productQuantityService, ProductDataEntityRepository productDataEntityRepository) {
        this.orderEntityService = orderEntityService;
        this.modelMapper = modelMapper;
        this.userEntityService = userEntityService;
        this.productQuantityService = productQuantityService;
        this.productDataEntityRepository = productDataEntityRepository;
    }

    public OrderEntityDTO EntityToDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderEntityDTO.class);
    }

    public OrderEntity DTOToEntity(OrderEntityDTO orderEntityDTO) {
        return modelMapper.map(orderEntityDTO, OrderEntity.class);
    }

    public ProductQuantityDTO pqEntityToDTO(ProductQuantityEntity productQuantityEntity) {
        return modelMapper.map(productQuantityEntity, ProductQuantityDTO.class);
    }

    public ProductQuantityEntity pqDTOToEntity(ProductQuantityDTO productQuantityEntityDTO) {
        return modelMapper.map(productQuantityEntityDTO, ProductQuantityEntity.class);
    }

    public UserEntityDTO userEntityToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserEntityDTO.class);
    }

    public UserEntity userDTOToEntity(UserEntityDTO userEntityDTO) {
        return modelMapper.map(userEntityDTO, UserEntity.class);
    }

    @GetMapping
    List<OrderEntityDTO> getCategoryEntityAll() {
        List<OrderEntityDTO> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityService.getOrdersAll()) {
            UserEntityDTO userEntityDTO = userEntityToDTO(orderEntity.getOrderUserEntity());
            OrderEntityDTO orderEntityDTO = EntityToDTO(orderEntity);
            orderEntityDTO.setCustomer(userEntityDTO);
            orders.add(orderEntityDTO);
        }
        return orders;
    }

    @GetMapping("/{id}")
    OrderEntityDTO getOrderEntityById(@PathVariable Long id) {
        Optional<OrderEntity> orderEntity = orderEntityService.getOrderById(id);
        if (orderEntity.isPresent()) {
            UserEntityDTO userEntityDTO = userEntityToDTO(orderEntity.get().getOrderUserEntity());
            OrderEntityDTO orderEntityDTO = EntityToDTO(orderEntity.get());
            orderEntityDTO.setCustomer(userEntityDTO);
            return orderEntityDTO;
        }
        return null;
    }

    @PostMapping
    OrderEntityDTO postOrderEntity(@Valid @RequestBody OrderEntityDTO orderEntityDTO) {
        OrderEntity orderEntity = DTOToEntity(orderEntityDTO);
        UserEntity userEntity = userDTOToEntity(orderEntityDTO.getCustomer());
        userEntity.setPassword("ya ne hochu refactorit usera");
        List<ProductQuantityEntity> productQuantityEntities = orderEntityService.checkOutQuan(orderEntityDTO).stream()
                .map(this::pqDTOToEntity)
                .collect(Collectors.toList());
        orderEntity.setOrderProductQuantityEntityList(productQuantityEntities);
        orderEntity.setOrderUserEntity(userEntity);

        Long price = 0L;
        for(ProductQuantityEntity pq: productQuantityEntities){
            if(productDataEntityRepository.findById(pq.getDataId()).isPresent()){
                price = price + productDataEntityRepository.findById(pq.getDataId()).get().getProductEntity().getPrice()*pq.getQuantity();
            }
        }
        orderEntity.setPrice(price);
        productQuantityService.saveProductQuantityAll(orderEntity.getOrderProductQuantityEntityList());
        userEntityService.saveUser(userEntity);
        orderEntityService.saveOrder(orderEntity);
        return orderEntityDTO;
    }

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Long id) {
        orderEntityService.deleteOrder(id);
    }

    @PutMapping("/{id}")
    OrderEntityDTO putOrderEntity(@PathVariable Long id, @Valid @RequestBody OrderEntityDTO orderEntityDTO) {
        Optional<OrderEntity> orderEntity = orderEntityService.getOrderById(id);
        OrderEntity newOrder = DTOToEntity(orderEntityDTO);
        if (orderEntity.isPresent()) {
            OrderEntity oldOrder = orderEntity.get();

            oldOrder.setCompleted(newOrder.getCompleted());
            oldOrder.setDestination(newOrder.getDestination());
            return orderEntityDTO;
        } else orderEntityService.saveOrder(newOrder);
        return null;
    }
}
