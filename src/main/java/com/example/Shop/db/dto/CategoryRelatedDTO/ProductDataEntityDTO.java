package com.example.Shop.db.dto.CategoryRelatedDTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"productDataId", "productDataColor", "productDataSize", "productDataQuantity"})
public class ProductDataEntityDTO {

    @JsonProperty(value = "productDataId", access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @NotNull
    @NotBlank
    @JsonProperty("productDataColor")
    public String color;

    @NotNull
    @JsonProperty("productDataSize")
    public int size;

    @NotNull
    @JsonProperty("productDataQuantity")
    public int quantity;


}

