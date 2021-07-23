package com.example.Shop.controllers;


import com.example.Shop.db.dto.OrderEntityDTO.OrderEntityDTO;
import com.example.Shop.db.dto.OrderEntityDTO.OrderEntityResponseDTO;
import com.example.Shop.db.dto.OrderEntityDTO.ProductEndResponseDTO;
import com.example.Shop.db.dto.OrderEntityDTO.ProductQuantityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductDataEntityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductEntityDTO;
import com.example.Shop.db.dto.UserRelatedDTO.UserEntityDTO;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.OrderRelatedEntites.OrderEntity;
import com.example.Shop.db.entities.OrderRelatedEntites.ProductQuantityEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.db.repos.ProductDataEntityRepository;
import com.example.Shop.services.OrderEntityService;
import com.example.Shop.services.ProductEntityService;
import com.example.Shop.services.ProductQuantityService;
import com.example.Shop.services.UserEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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
    final private ProductEntityService productEntityService;

    public OrderEntityController(OrderEntityService orderEntityService, ModelMapper modelMapper, UserEntityService userEntityService, ProductQuantityService productQuantityService, ProductDataEntityRepository productDataEntityRepository, ProductEntityService productEntityService) {
        this.orderEntityService = orderEntityService;
        this.modelMapper = modelMapper;
        this.userEntityService = userEntityService;
        this.productQuantityService = productQuantityService;
        this.productDataEntityRepository = productDataEntityRepository;
        this.productEntityService = productEntityService;
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


    public ProductEntityDTO proEntityToDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductEntityDTO.class);
    }

    public ProductEntity proDTOToEntity(ProductEntityDTO productEntityDTO) {
        return modelMapper.map(productEntityDTO, ProductEntity.class);
    }

    public ProductDataEntityDTO pdEntityToDTO(ProductDataEntity productDataEntity) {
        return modelMapper.map(productDataEntity, ProductDataEntityDTO.class);
    }

    public ProductDataEntity pdDTOToEntity(ProductDataEntityDTO productDataEntityDTO) {
        return modelMapper.map(productDataEntityDTO, ProductDataEntity.class);
    }

    public OrderEntityResponseDTO convertToEndDTO(OrderEntity orderEntity){
        OrderEntityResponseDTO order = new OrderEntityResponseDTO();
        order.setId(orderEntity.getId());
        order.setIsPaid(orderEntity.getIsPaid());
        order.setDestination(orderEntity.getDestination());
        order.setState(orderEntity.getState());
        order.setCompleted(orderEntity.getCompleted());
        order.setCustomer(userEntityToDTO(orderEntity.getOrderUserEntity()));
        order.setPrice(orderEntity.getPrice());
        order.setState(orderEntity.getState());
        Set<ProductEndResponseDTO> products = new HashSet<>();

        for(ProductQuantityEntity productQuantityEntity: orderEntity.getOrderProductQuantityEntityList()){
            Optional<ProductDataEntity> productDataEntity = productDataEntityRepository.findById(productQuantityEntity.getDataId());
            if(productDataEntity.isPresent()){
                ProductDataEntity productDataEntity1 = productDataEntity.get();
                ProductEntity productEntity = productDataEntity1.getProductEntity();
                ProductEndResponseDTO productEndResponseDTO = new ProductEndResponseDTO();
                productEndResponseDTO.setProduct(proEntityToDTO(productEntity));
                products.add(productEndResponseDTO);
            }
        }
        for(ProductEndResponseDTO productEndResponseDTO: products){
            List<ProductDataEntityDTO> details = new ArrayList<>();
            for(ProductQuantityEntity productQuantityEntity: orderEntity.getOrderProductQuantityEntityList()){
                ProductDataEntity productDataEntity = productDataEntityRepository.findById(productQuantityEntity.getDataId()).get();
                if(productDataEntity.getProductEntity().getId().equals(productEndResponseDTO.getProduct().getId())){
                    ProductDataEntityDTO productDataEntityDTO = pdEntityToDTO(productDataEntity);
                    productDataEntityDTO.setQuantity(productQuantityEntity.getQuantity());
                    details.add(productDataEntityDTO);
                }
            }
            productEndResponseDTO.setDetails(details);
        }
        ArrayList<ProductEndResponseDTO> productsList = new ArrayList<>();
        productsList.addAll(products);
        order.setProductList(productsList);

        return order;
    }

    @GetMapping("/test")
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

    @GetMapping
    List<OrderEntityResponseDTO> getCategoryEntityAllTest() {
        List<OrderEntityDTO> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityService.getOrdersAll()) {
            UserEntityDTO userEntityDTO = userEntityToDTO(orderEntity.getOrderUserEntity());
            OrderEntityDTO orderEntityDTO = EntityToDTO(orderEntity);
            orderEntityDTO.setCustomer(userEntityDTO);
            orders.add(orderEntityDTO);
        }
        return orderEntityService.getOrdersAll().stream()
                .map(this::convertToEndDTO)
                .collect(Collectors.toList());
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
            Optional<ProductDataEntity> productDataEntity = productDataEntityRepository.findById(pq.getDataId());
            if(productDataEntity.isPresent()){
                ProductEntity productEntity = productDataEntity.get().getProductEntity();
                if(productEntity!=null){
                    price = price + productDataEntity.get().getProductEntity().getPrice()*pq.getQuantity();
                }
            }
        }
        if(price>0L){
            orderEntity.setPrice(price);
            productQuantityService.saveProductQuantityAll(orderEntity.getOrderProductQuantityEntityList());
            userEntityService.saveUser(userEntity);
            orderEntityService.saveOrder(orderEntity);
            return orderEntityDTO;
        }

        return null;
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
