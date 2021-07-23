package com.example.Shop.db.dto.ProductRelatedDTO;


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

    @JsonProperty("productDataSize")
    public Long size;

    @JsonProperty("productDataQuantity")
    public Long quantity;


}

