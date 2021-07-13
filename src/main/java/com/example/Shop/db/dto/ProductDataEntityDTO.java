package com.example.Shop.db.dto;

import com.example.Shop.db.entities.ProductRelatedEntities.CategoryEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.ProductDataEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class ProductDataEntityDTO {
    public Long id;
    public String color;
    public int size;
    public int quantity;

}
