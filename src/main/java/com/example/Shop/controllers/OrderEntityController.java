package com.example.Shop.controllers;


import com.example.Shop.db.dto.BotRelatedDTO.OrderCompletedDTO;
import com.example.Shop.db.dto.BotRelatedDTO.OrderStateDTO;
import com.example.Shop.db.dto.OrderEntityDTO.OrderEntityDTO;
import com.example.Shop.db.dto.OrderEntityDTO.OrderEntityResponseDTO;
import com.example.Shop.db.dto.OrderEntityDTO.ProductEndResponseDTO;
import com.example.Shop.db.dto.OrderEntityDTO.ProductQuantityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductDataEntityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductEntityDTO;
import com.example.Shop.db.dto.UserRelatedDTO.UserEntityDTO;
import com.example.Shop.db.entities.OrderRelatedEntites.OrderEntity;
import com.example.Shop.db.entities.OrderRelatedEntites.ProductQuantityEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.db.repos.ProductDataEntityRepository;
import com.example.Shop.services.OrderEntityService;
import com.example.Shop.services.ProductEntityService;
import com.example.Shop.services.ProductQuantityService;
import com.example.Shop.services.UserEntityService;
import io.swagger.annotations.Api;
import org.camunda.bpm.engine.RuntimeService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
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
    final private RuntimeService runtimeService;

    public OrderEntityController(OrderEntityService orderEntityService, ModelMapper modelMapper, UserEntityService userEntityService, ProductQuantityService productQuantityService, ProductDataEntityRepository productDataEntityRepository, ProductEntityService productEntityService, RuntimeService runtimeService) {
        this.orderEntityService = orderEntityService;
        this.modelMapper = modelMapper;
        this.userEntityService = userEntityService;
        this.productQuantityService = productQuantityService;
        this.productDataEntityRepository = productDataEntityRepository;
        this.productEntityService = productEntityService;
        this.runtimeService = runtimeService;
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

    public OrderEntityResponseDTO convertToEndDTO(OrderEntity orderEntity) {
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

        for (ProductQuantityEntity productQuantityEntity : orderEntity.getOrderProductQuantityEntityList()) {
            Optional<ProductDataEntity> productDataEntity = productDataEntityRepository.findById(productQuantityEntity.getDataId());
            if (productDataEntity.isPresent()) {
                ProductDataEntity productDataEntity1 = productDataEntity.get();
                ProductEntity productEntity = productDataEntity1.getProductEntity();
                ProductEndResponseDTO productEndResponseDTO = new ProductEndResponseDTO();
                productEndResponseDTO.setProduct(proEntityToDTO(productEntity));

                List<Long> ids = products.stream()
                        .map(pe -> pe.getProduct().getId())
                        .collect(Collectors.toList());

                if (!(ids.contains(productEndResponseDTO.getProduct().getId()))) {
                    products.add(productEndResponseDTO);
                }
            }
        }
        for (ProductEndResponseDTO productEndResponseDTO : products) {
            List<ProductDataEntityDTO> details = new ArrayList<>();
            for (ProductQuantityEntity productQuantityEntity : orderEntity.getOrderProductQuantityEntityList()) {
                ProductDataEntity productDataEntity = productDataEntityRepository.findById(productQuantityEntity.getDataId()).get();
                if (productDataEntity.getProductEntity().getId().equals(productEndResponseDTO.getProduct().getId())) {
                    ProductDataEntityDTO productDataEntityDTO = pdEntityToDTO(productDataEntity);
                    productDataEntityDTO.setQuantity(productQuantityEntity.getQuantity());
                    if ((!details.contains(productDataEntityDTO))) {
                        details.add(productDataEntityDTO);
                    }
                }
            }
            productEndResponseDTO.setDetails(details);
        }

        List<ProductEndResponseDTO> productsList = new ArrayList<>(products);
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
        return orderEntityService.getOrdersAll().stream()
                .map(this::convertToEndDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    OrderEntityResponseDTO getOrderEntityById(@PathVariable Long id) {
        Optional<OrderEntity> orderEntity1 = orderEntityService.getOrderById(id);
        if (orderEntity1.isPresent()) {
            return convertToEndDTO(orderEntity1.get());
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
        for (ProductQuantityEntity productQuantityEntity : productQuantityEntities) {
            productQuantityEntity.setOrder(orderEntity);
        }

        Long price = 0L;
        for (ProductQuantityEntity pq : productQuantityEntities) {
            Optional<ProductDataEntity> productDataEntity = productDataEntityRepository.findById(pq.getDataId());
            if (productDataEntity.isPresent()) {
                ProductEntity productEntity = productDataEntity.get().getProductEntity();
                if (productEntity != null) {
                    price = price + productDataEntity.get().getProductEntity().getPrice() * pq.getQuantity();
                }
            }
        }
        if (price > 0L) {
            orderEntity.setPrice(price);

            userEntityService.saveUser(userEntity);
            orderEntityService.saveOrder(orderEntity);
            productQuantityService.saveProductQuantityAll(orderEntity.getOrderProductQuantityEntityList());
            runtimeService.createProcessInstanceByKey("Process_0o6v8bi")
                    .businessKey(orderEntity.getId().toString())
                    .execute();

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

    @PutMapping("/{id}/complete/")
    void putBotComplete(@PathVariable Long id, @Valid @RequestBody OrderCompletedDTO orderCompleteDTO) {
        Optional<OrderEntity> orderEntity = orderEntityService.getOrderById(id);
        if (orderEntity.isPresent()) {
            OrderEntity oldOrder = orderEntity.get();
            oldOrder.setCompleted(orderCompleteDTO.getCompleted());
            orderEntityService.saveOrder(oldOrder);
        }
    }

    @PutMapping("/{id}/state/")
    void putBotState(@PathVariable Long id, @Valid @RequestBody OrderStateDTO orderStateDTO) {
        Optional<OrderEntity> orderEntity = orderEntityService.getOrderById(id);
        if (orderEntity.isPresent()) {
            OrderEntity oldOrder = orderEntity.get();
            oldOrder.setState(orderStateDTO.getState());
            if (orderStateDTO.getState().equals("????????????")) {
                if (oldOrder.getIsPaid()) {
                    runtimeService.createMessageCorrelation("Message_1h8irgj")
                            .processInstanceBusinessKey(id.toString())
                            .setVariable("paid", oldOrder.getIsPaid())
                            .correlate();
                } else {
                    runtimeService.createMessageCorrelation("Message_1h8irgj")
                            .processInstanceBusinessKey(id.toString())
                            .setVariable("paid", oldOrder.getIsPaid())
                            .setVariable("deadlinePayments", Date.from(Instant.now().plusSeconds(180L)))
                            .correlate();
                }

            }
            orderEntityService.saveOrder(oldOrder);
        }
    }

    @GetMapping("/{id}/tpay")
    void fakePay(@PathVariable Long id) {
        Optional<OrderEntity> orderEntity = orderEntityService.getOrderById(id);
        if (orderEntity.isPresent()) {
            OrderEntity orderEntity1 = orderEntity.get();
            orderEntity1.setIsPaid(true);
            orderEntityService.saveOrder(orderEntity1);
        }
    }

    @GetMapping("/{id}/pay")
    Long payWait(@PathVariable Long id) {
        Optional<OrderEntity> orderEntity = orderEntityService.getOrderById(id);
        if (orderEntity.isPresent()) {
            OrderEntity orderEntity1 = orderEntity.get();
            runtimeService.createMessageCorrelation("Message_24fd06q")
                    .processInstanceBusinessKey(id.toString())
                    .setVariable("deadlinePayments", LocalDateTime.now().plusMinutes(2L))
                    .correlate();
            return orderEntity1.getPrice();
        }
        return null;
    }

    @GetMapping("/{id}/answer")
    Long otvetKek(@PathVariable Long id) {
        Optional<OrderEntity> orderEntity = orderEntityService.getOrderById(id);
        if (orderEntity.isPresent()) {
            runtimeService.createMessageCorrelation("Message_1bknnqf")
                    .processInstanceBusinessKey(id.toString())
                    .correlate();
            return 1L;
        }
        return 0L;
    }


}
