package com.example.Shop.db.dto.CategoryRelatedDTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"productDataId", "productDataColor", "productDataSize", "productDataQuantity"})
public class ProductDataEntityDTO {

    @NotNull
    @JsonProperty("productDataId")
    public Long id;

    @NotNull
    @JsonProperty("productDataColor")
    public String color;

    @NotNull
    @JsonProperty("productDataSize")
    public int size;

    @NotNull
    @JsonProperty("productDataQuantity")
    public int quantity;


}

