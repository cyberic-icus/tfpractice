package com.example.Shop.db.dto;

import com.example.Shop.db.entities.ProductRelatedEntities.CartEntity;
import com.example.Shop.db.entities.ProductRelatedEntities.CategoryEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class CategoryEntityDTO {
    public Long id;
    public String name;
    public String description;
}
