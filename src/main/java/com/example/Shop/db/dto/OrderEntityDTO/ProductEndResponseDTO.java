package com.example.Shop.db.dto.OrderEntityDTO;

import com.example.Shop.db.dto.ProductRelatedDTO.ProductDataEntityDTO;
import com.example.Shop.db.dto.ProductRelatedDTO.ProductEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEndResponseDTO {
    ProductEntityDTO product;
    List<ProductDataEntityDTO> details;
}
