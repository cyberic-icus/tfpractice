package com.example.Shop.db.dto.OrderEntityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"productQuantityId", "productDataId", "quantity"})
public class ProductQuantityDTO {
    @JsonProperty(value = "productQuantityId", access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @NotNull
    public Long productDataId;

    @NotNull
    public Long quantity;

}
