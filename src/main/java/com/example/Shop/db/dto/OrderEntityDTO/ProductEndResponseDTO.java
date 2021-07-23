package com.example.Shop.db.dto.OrderEntityDTO;

import com.example.Shop.db.dto.ProductRelatedDTO.ProductDataEntityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductEntityDTO;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEndResponseDTO {
    ProductEntityDTO product;
    List<ProductDataEntityDTO> details;
}
