package com.example.Shop.db.dto.OrderEntityDTO;


import com.example.Shop.db.dto.UserRelatedDTO.UserEntityDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"orderId", "completed", "price", "orderDestination", "customer", "productList"})
public class OrderEntityDTO {

    @JsonProperty(value = "orderId", access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @NotNull
    @NotBlank
    @JsonProperty("orderDestination")
    public String destination;

    @NotNull
    public UserEntityDTO customer;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Boolean completed = false;
    @NotNull
    List<ProductQuantityDTO> productList = new ArrayList<>();
}