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
@JsonPropertyOrder({"orderId", "isPaid", "orderState", "completed", "price", "orderDestination", "customer", "productList"})
public class OrderEntityResponseDTO {
    @JsonProperty(value = "orderId", access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @NotNull
    @NotBlank
    @JsonProperty("orderDestination")
    public String destination;


    @JsonProperty(value = "orderState", access = JsonProperty.Access.READ_ONLY)
    public String state;


    @NotNull
    public UserEntityDTO customer;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Boolean completed = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Boolean isPaid = false;

    @NotNull
    List<ProductEndResponseDTO> productList = new ArrayList<>();
}