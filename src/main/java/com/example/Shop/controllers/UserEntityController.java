package com.example.Shop.controllers;

import com.example.Shop.db.dto.OrderEntityDTO.OrderEntityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductDataEntityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductEntityDTO;
import com.example.Shop.db.dto.UserRelatedDTO.RoleEntityDTO;
import com.example.Shop.db.dto.UserRelatedDTO.UserEntityDTO;
import com.example.Shop.db.entities.OrderRelatedEntites.OrderEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.RoleEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.example.Shop.db.repos.OrderEntityRepository;
import com.example.Shop.services.ProductDataEntityService;
import com.example.Shop.services.ProductEntityService;
import com.example.Shop.services.RoleEntityService;
import com.example.Shop.services.UserEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Api
@CrossOrigin(origins = {"http://localhost:4200/", "https://summer-practy.herokuapp.com/"}, maxAge = 3600)
public class UserEntityController {
    final UserEntityService userEntityService;
    final RoleEntityService roleEntityService;
    final OrderEntityRepository orderEntityRepository;
    final ProductEntityService productEntityService;
    final ProductDataEntityService productDataEntityService;
    final ModelMapper modelMapper;

    public UserEntityController(UserEntityService userEntityService, RoleEntityService roleEntityService, OrderEntityRepository orderEntityRepository, ProductEntityService productEntityService, ProductDataEntityService productDataEntityService, ModelMapper modelMapper) {
        this.userEntityService = userEntityService;
        this.roleEntityService = roleEntityService;
        this.orderEntityRepository = orderEntityRepository;
        this.productEntityService = productEntityService;
        this.productDataEntityService = productDataEntityService;
        this.modelMapper = modelMapper;
    }

    public UserEntityDTO userEntityToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserEntityDTO.class);
    }

    public UserEntity userDTOToEntity(UserEntityDTO userEntityDTO) {
        return modelMapper.map(userEntityDTO, UserEntity.class);
    }

    public RoleEntityDTO roleEntityToDTO(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, RoleEntityDTO.class);
    }

    public RoleEntity roleDTOToEntity(RoleEntityDTO roleEntityDTO) {
        return modelMapper.map(roleEntityDTO, RoleEntity.class);
    }

    public OrderEntityDTO orderEntityToDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderEntityDTO.class);
    }

    public OrderEntity orderDTOToEntity(OrderEntityDTO orderEntityDTO) {
        return modelMapper.map(orderEntityDTO, OrderEntity.class);
    }

    public ProductEntityDTO productEntityToDTO(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductEntityDTO.class);
    }

    public ProductEntity productDTOToEntity(ProductEntityDTO productEntityDTO) {
        return modelMapper.map(productEntityDTO, ProductEntity.class);
    }

    public ProductDataEntityDTO dataEntityToDTO(ProductDataEntity productDataEntity) {
        return modelMapper.map(productDataEntity, ProductDataEntityDTO.class);
    }

    public ProductDataEntity dataDTOToEntity(ProductDataEntityDTO productDataEntityDTO) {
        return modelMapper.map(productDataEntityDTO, ProductDataEntity.class);
    }


    @GetMapping
    public List<UserEntityDTO> getUserEntityAll() {
        return userEntityService.getUsersAll().stream()
                .map(this::userEntityToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserEntityDTO getUserById(@PathVariable Long userId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            return userEntityToDTO(userEntity.get());
        }
        return null;
    }

    @PostMapping
    public UserEntityDTO postUser(@Valid @RequestBody UserEntityDTO userEntityDTO) {
        if (userEntityDTO != null) {
            userEntityService.saveUser(userDTOToEntity(userEntityDTO));
            return userEntityDTO;
        }
        return null;
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            userEntityService.deleteUserById(userId);
        }
    }

    @PutMapping("/{userId}")
    public void putUserById(@PathVariable Long userId, @Valid @RequestBody UserEntityDTO userEntityDTO) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if ((userEntity.isPresent()) && (userEntityDTO != null)) {
            UserEntity oldUser = userEntity.get();
            UserEntity newUser = userDTOToEntity(userEntityDTO);
            oldUser.setPassword(newUser.getPassword());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setUsername(newUser.getUsername());
            oldUser.setFirstName(newUser.getFirstName());
            oldUser.setLastName(newUser.getLastName());
            oldUser.setRoles(newUser.getRoles());
            oldUser.setPhoneNumber(newUser.getPhoneNumber());
            userEntityService.saveUser(oldUser);

        } else if (userEntityDTO != null) {
            userEntityService.saveUser(userDTOToEntity(userEntityDTO));
        }
    }

    @GetMapping("/{userId}/roles")
    public List<RoleEntityDTO> getRoleEntityAll(@PathVariable Long userId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            return userEntity.get().getRoles().stream()
                    .map(this::roleEntityToDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/{userId}/orders")
    public List<OrderEntityDTO> getUserOrdersAll(@PathVariable Long userId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            return userEntity.get().getOrders().stream()
                    .map(this::orderEntityToDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @PostMapping("/{userId}/orders")
    public OrderEntityDTO postUserOrder(@PathVariable Long userId, @Valid @RequestBody OrderEntityDTO orderEntityDTO) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            OrderEntity orderEntity = orderDTOToEntity(orderEntityDTO);
            UserEntity userEntity1 = userEntity.get();


            userEntity1.getOrders().add(orderEntity);
            orderEntity.setOrderUserEntity(userEntity1);
            orderEntityRepository.save(orderEntity);
            return orderEntityDTO;
        }
        return null;
    }


    @DeleteMapping("/{userId}/orders/{orderId}")
    public void deleteUserOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = orderEntityRepository.findById(orderId);
            if (orderEntity.isPresent()) {
                if (userEntity.get().getOrders().contains(orderEntity.get())) {
                    orderEntityRepository.delete(orderEntity.get());
                }
            }
        }
    }

//    @PutMapping("/{userId}/orders/{orderId}")
//    public void putUserOrder(@PathVariable Long userId, @PathVariable Long orderId, @Valid @RequestBody OrderEntityDTO orderEntityDTO) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if ((userEntity.isPresent()) && (orderEntityDTO != null)) {
//            Optional<OrderEntity> orderEntity1 = userEntity.get().getOrders().stream()
//                    .filter(oe -> oe.getId().equals(orderId))
//                    .findAny();
//            if (orderEntity1.isPresent()) {
//                OrderEntity oldOrder = orderEntity1.get();
//                OrderEntity newOrder = orderDTOToEntity(orderEntityDTO);
//                oldOrder.set
//                orderEntity1.get().setOrderProductQuantityEntityList(orderEntity.getOrderProductQuantityEntityList());
//                orderEntityRepository.save(orderEntity1.get());
//            }
//        } else if ((userEntity.isPresent()) && orderEntity == null) {
//            userEntity.get().getOrders().add(orderEntity);
//            orderEntity.setOrderUserEntity(userEntity.get());
//            userEntityService.saveUser(userEntity.get());
//
//        }
//    }

    @GetMapping("/{userId}/orders/{orderId}")
    public OrderEntityDTO getUserOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream()
                    .filter(oe -> oe.getId().equals(orderId))
                    .findAny();
            if (orderEntity.isPresent()) {
                return orderEntityToDTO(orderEntity.get());
            }


        }
        return null;
    }

//    @GetMapping("/{userId}/orders/{orderId}/products")
//    public List<ProductEntityDTO> getUserOrdersProducts(@PathVariable Long userId, @PathVariable Long orderId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                return orderEntity.get().getOrderProductQuantityEntityList().stream()
//                        .map(this::productEntityToDTO)
//                        .collect(Collectors.toList());
//            }
//
//        }
//        return null;
//    }

//    @PostMapping("/{userId}/orders/{orderId}/products")
//    public ProductEntityDTO postProductToOrder(@PathVariable Long userId,
//                                               @PathVariable Long orderId,
//                                               @Valid @RequestBody ProductEntityDTO productEntityDTO) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                ProductEntity productEntity = productDTOToEntity(productEntityDTO);
//                orderEntity.get().getOrderProductQuantityEntityList().add(productEntity);
//                productEntity.getProductOrders().add(orderEntity.get());
//                productEntityService.saveProduct(productEntity);
//                return productEntityDTO;
//            }
//
//        }
//        return null;
//    }

//
//    @GetMapping("/{userId}/orders/{orderId}/products/{productId}")
//    public ProductEntityDTO getUserOrdersProduct(@PathVariable Long userId,
//                                                 @PathVariable Long orderId,
//                                                 @PathVariable Long productId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = orderEntity.get().getOrderProductQuantityEntityList().stream()
//                        .filter(pe -> pe.getId().equals(productId))
//                        .findAny();
//                if(productEntity.isPresent()){
//                    return productEntityToDTO(productEntity.get());
//                }
//            }
//        }
//        return null;
//    }

//    @DeleteMapping("/{userId}/orders/{orderId}/products/{productId}")
//    public void deleteUserOrderProduct(@PathVariable Long userId,
//                                       @PathVariable Long orderId,
//                                       @PathVariable Long productId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = orderEntity.get().getOrderProductQuantityEntityList().stream().filter(pe -> pe.getId().equals(productId)).findAny();
//                if (productEntity.isPresent()) {
//                    productEntityService.deleteProductById(productId);
//                }
//            }
//        }
//    }

//    @PutMapping("/{userId}/orders/{orderId}/products/{productId}")
//    public void putUserOrderProduct(@PathVariable Long userId,
//                                    @PathVariable Long orderId,
//                                    @PathVariable Long productId,
//                                    @Valid @RequestBody ProductEntityDTO productEntityDTO) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity1 = orderEntity.get().getOrderProductQuantityEntityList().stream().filter(pe -> pe.getId().equals(productId)).findAny();
//                ProductEntity newProduct = productDTOToEntity(productEntityDTO);
//                if(productEntity1.isPresent()){
//                    ProductEntity oldProduct = productEntity1.get();
//                    oldProduct.setProductName(newProduct.getProductName());
//                    oldProduct.setProductDescription(newProduct.getProductDescription());
//                    oldProduct.setPrice(newProduct.getPrice());
//                    oldProduct.setImageUrl(newProduct.getImageUrl());
//                    oldProduct.setCreatedDate(newProduct.getCreatedDate());
//                } else productEntityService.saveProduct(newProduct);
//            }
//        }
//
//    }

//
//    @GetMapping("/{userId}/orders/{orderId}/products/{productId}/info")
//    public List<ProductDataEntityDTO> getUserOrdersProductsDataEntities(@PathVariable Long userId,
//                                                                        @PathVariable Long orderId,
//                                                                        @PathVariable Long productId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = orderEntity.get().getOrderProductQuantityEntityList().stream().filter(pe -> pe.getId().equals(productId)).findAny();
//                if (productEntity.isPresent()) {
//                    return productEntity.get().getSizesAndColors().stream()
//                            .map(this::dataEntityToDTO)
//                            .collect(Collectors.toList());
//                }
//            }
//        }
//        return null;
//    }
//
//    @PostMapping("/{userId}/orders/{orderId}/products/{productId}/info")
//    public ProductDataEntityDTO getUserOrdersProductsDataEntities(@PathVariable Long userId,
//                                                               @PathVariable Long orderId,
//                                                               @PathVariable Long productId,
//                                                               @Valid @RequestBody ProductDataEntityDTO productDataEntity) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream()
//                    .filter(oe -> oe.getId().equals(orderId))
//                    .findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = orderEntity.get().getOrderProductQuantityEntityList().stream()
//                        .filter(pe -> pe.getId().equals(productId))
//                        .findAny();
//                if (productEntity.isPresent()) {
//                    productDataEntityService.saveProductData(dataDTOToEntity(productDataEntity));
//                }
//            }
//        }
//        return null;
//    }

//    @GetMapping("/{userId}/orders/{orderId}/products/{productId}/info/{dataId}")
//    public ProductDataEntityDTO getUserOrdersProductsDataEntity(@PathVariable Long userId,
//                                                                       @PathVariable Long orderId,
//                                                                       @PathVariable Long productId,
//                                                                       @PathVariable Long dataId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream()
//                    .filter(oe -> oe.getId().equals(orderId))
//                    .findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = orderEntity.get().getOrderProductQuantityEntityList().stream()
//                        .filter(pe -> pe.getId().equals(productId))
//                        .findAny();
//                if (productEntity.isPresent()) {
//                    Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream()
//                            .filter(pde -> pde.getId().equals(dataId))
//                            .findAny();
//                    if(productDataEntity.isPresent()){
//                        return dataEntityToDTO(productDataEntity.get());
//                    }
//                }
//            }
//        }
//        return null;
//    }

//    @DeleteMapping("/{userId}/orders/{orderId}/products/{productId}/info/{dataId}")
//    public void deleteUserOrderProductData(@PathVariable Long userId, @PathVariable Long orderId, @PathVariable Long productId, @PathVariable Long dataId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = orderEntity.get().getOrderProductQuantityEntityList().stream().filter(pe -> pe.getId().equals(productId)).findAny();
//                if (productEntity.isPresent()) {
//                    Optional<ProductDataEntity> productDataEntity = productEntity.get().getSizesAndColors().stream().filter(pde -> pde.getId().equals(dataId)).findAny();
//                    if (productDataEntity.isPresent()) {
//                        productDataEntityService.deleteProductDataById(dataId);
//                    }
//                }
//
//            }
//
//        }
//    }

//    @PutMapping("/{userId}/orders/{orderId}/products/{productId}/info/{dataId}")
//    public void putUserProductData(@PathVariable Long userId,
//                                   @PathVariable Long orderId,
//                                   @PathVariable Long productId,
//                                   @PathVariable Long dataId,
//                                   @Valid @RequestBody ProductDataEntityDTO productDataEntityDTO) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<OrderEntity> orderEntity = userEntity.get().getOrders().stream().filter(oe -> oe.getId().equals(orderId)).findAny();
//            if (orderEntity.isPresent()) {
//                Optional<ProductEntity> productEntity = orderEntity.get().getOrderProductQuantityEntityList().stream().filter(pe -> pe.getId().equals(productId)).findAny();
//                if (productEntity.isPresent()) {
//                    Optional<ProductDataEntity> productDataEntity1 = productEntity.get().getSizesAndColors().stream().filter(pde -> pde.getId().equals(dataId)).findAny();
//                    if (productDataEntity1.isPresent()) {
//                        ProductDataEntity productDataEntity = dataDTOToEntity(productDataEntityDTO);
//                        productDataEntity1.get().setColor(productDataEntity.getColor());
//                        productDataEntity1.get().setQuantity(productDataEntity.getQuantity());
//                        productDataEntity1.get().setSize(productDataEntity.getSize());
//                        productDataEntityService.saveProductData(productDataEntity1.get());
//                    }
//                }
//            }
//        }
//    }


//    @GetMapping("/{userId}/cart")
//    public List<ProductEntityDTO> getUserCart(@PathVariable Long userId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            return userEntity.get().getCartEntity().getCartProducts().stream()
//                    .map(this::productEntityToDTO)
//                    .collect(Collectors.toList());
//        }
//        return null;
//    }
//
//    @PostMapping("/{userId}/cart")
//    public ProductEntity getUserCart(@PathVariable Long userId, @Valid @RequestBody ProductEntityDTO productEntityDTO) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            ProductEntity productEntity = productDTOToEntity(productEntityDTO);
//            userEntity.get().getCartEntity().getCartProducts().add(productEntity);
//            productEntity.getProductCarts().add(userEntity.get().getCartEntity());
//            userEntityService.saveUser(userEntity.get());
//        }
//        return null;
//    }

//    @PutMapping("/{userId}/cart/")
//    public ProductEntity putUserCart(@PathVariable Long userId, @PathVariable Long productId, @RequestBody CartEntity cartEntity) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            CartEntity cartEntity1 = userEntity.get().getCartEntity();
//            cartEntity1.setCartProducts(cartEntity.getCartProducts());
//            userEntityService.saveUser(userEntity.get());
//        }
//        return null;
//    }

//    @DeleteMapping("/{userId}/cart/")
//    public void deleteUserCart(@PathVariable Long userId, @PathVariable Long productId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            CartEntity cartEntity = userEntity.get().getCartEntity();
//            cartEntity.setCartProducts(new HashSet<>());
//        }
//    }

//    @GetMapping("/{userId}/cart/products")
//    public Iterable<ProductEntity> getUserCartProducts(@PathVariable Long userId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            return userEntity.get().getCartEntity().getCartProducts();
//        }
//        return null;
//    }

//    @PostMapping("/{userId}/cart/products")
//    public ProductEntity postUserCartProduct(@PathVariable Long userId, @RequestBody ProductEntity productEntity) {
//        Optional<UserEntity> cartUserEntity = userEntityService.getUserById(userId);
//        if (cartUserEntity.isPresent()) {
//            cartUserEntity.get().getProductCarts().getCartProducts().add(productEntity);
//            productEntity.setProductCarts(cartUserEntity.get().getProductCarts());
//            userEntityService.saveUser(cartUserEntity.get());
//        }
//        return null;
//    }
//
//    @PutMapping("/{userId}/cart/{productId}")
//    public ProductEntity putUserCartProduct(@PathVariable Long userId, @PathVariable Long productId, @RequestBody ProductEntity productEntity) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<ProductEntity> productEntity1 = userEntity.get().getCartEntity().getCartProducts().stream().filter(pe -> pe.getId().equals(productId)).findAny();
//            if (productEntity1.isPresent()) {
//                productEntity1.get().setPrice(productEntity.getPrice());
//                productEntity1.get().setProductName(productEntity.getProductName());
//                productEntity1.get().setImageUrl(productEntity.getImageUrl());
//                productEntity1.get().setSizesAndColors(productEntity.getSizesAndColors());
//                productEntity1.get().setCreatedDate(productEntity.getCreatedDate());
//                productEntity1.get().setProductDescription(productEntity.getProductDescription());
//                userEntityService.saveUser(userEntity.get());
//            }
//        }
//        return null;
//    }
//
//    @DeleteMapping("/{userId}/cart/{productId}")
//    public void deleteUserCartProdcut(@PathVariable Long userId, @PathVariable Long productId) {
//        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
//        if (userEntity.isPresent()) {
//            Optional<ProductEntity> productEntity1 = userEntity.get().getCartEntity().getCartProducts().stream().filter(pe -> pe.getId().equals(productId)).findAny();
//            if (productEntity1.isPresent()) {
//                productEntityService.deleteProductById(productId);
//            }
//        }
//    }


    @GetMapping("/{userId}/roles/")
    public List<RoleEntityDTO> getRoleEntity(@PathVariable Long userId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            return userEntity.get().getRoles().stream()
                    .map(this::roleEntityToDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/{userId}/roles/{roleId}/")
    public RoleEntityDTO getRoleEntity(@PathVariable Long userId, @PathVariable Long roleId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        Optional<RoleEntity> userAuthority = roleEntityService.getRoleEntityById(roleId);
        if ((userEntity.isPresent()) && userAuthority.isPresent()) {
            Iterable<RoleEntity> userAuthorities = userEntity.get().getRoles();
            Optional<RoleEntity> userAuthority1 = userAuthority.stream()
                    .filter(auth -> userAuthority.get().equals(auth))
                    .findAny();
            if (userAuthority1.isPresent()) {
                return roleEntityToDTO(userAuthority1.get());
            }
        }
        return null;
    }

    @DeleteMapping("/{userId}/roles/{roleId}/")
    public void deleteRoleEntity(@PathVariable Long userId, @PathVariable Long roleId) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        Optional<RoleEntity> userAuthority = roleEntityService.getRoleEntityById(roleId);
        if ((userEntity.isPresent()) && userAuthority.isPresent()) {
            Iterable<RoleEntity> userAuthorities = userEntity.get().getRoles();
            Optional<RoleEntity> userAuthority1 = userAuthority.stream().filter(auth -> userAuthority.get().equals(auth)).findAny();
            if (userAuthority1.isPresent()) {
                userEntity.get().getRoles().remove(userAuthority1.get());
                userEntityService.saveUser(userEntity.get());
            }
        }
    }

    @PostMapping("/{userId}/roles/")
    public RoleEntity postRoleEntity(@PathVariable Long userId, @Valid @RequestBody RoleEntityDTO roleEntityDTO) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            RoleEntity roleEntity = roleDTOToEntity(roleEntityDTO);
            userEntity.get().getRoles().add(roleEntity);
            userEntityService.saveUser(userEntity.get());
        }
        return null;
    }

    @PutMapping("/{userId}/roles/{roleId}/")
    public void putRoleEntity(@PathVariable Long userId,
                              @PathVariable Long roleId,
                              @Valid @RequestBody RoleEntityDTO roleEntityDTO) {
        Optional<UserEntity> userEntity = userEntityService.getUserById(userId);
        if (userEntity.isPresent()) {
            Optional<RoleEntity> roleEntity = userEntity.get().getRoles().stream()
                    .filter(role -> role.getId().equals(roleId))
                    .findAny();
            if (roleEntity.isPresent()) {
                RoleEntity oldRole = roleEntity.get();
                RoleEntity newRole = roleDTOToEntity(roleEntityDTO);
                oldRole.setName(newRole.getName());
                roleEntityService.saveRole(oldRole);
            }
        }
    }
}
