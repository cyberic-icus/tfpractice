package com.example.Shop.controllers;

import com.example.Shop.db.dto.CategoryEntityDTO;
import com.example.Shop.db.entities.ProductRelatedEntities.CategoryEntity;
import com.example.Shop.services.CategoryEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/category")
@CrossOrigin(origins = {"http://localhost:4200/","https://summer-practy.herokuapp.com/"}, maxAge = 3600)
public class CategoryEntityController {
    @Autowired
    CategoryEntityService categoryEntityService;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryEntityDTO EntityToDTO(CategoryEntity categoryEntity){
        return modelMapper.map(categoryEntity, CategoryEntityDTO.class);
    }

    public CategoryEntity DTOToEntity(CategoryEntityDTO categoryEntityDTO){
        return modelMapper.map(categoryEntityDTO, CategoryEntity.class);
    }


    @GetMapping
    List<CategoryEntityDTO> getCategoryEntityAll() {
        List<CategoryEntity> categoryEntities = (List<CategoryEntity>) categoryEntityService.getCategoryAll();
        return categoryEntities.stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    CategoryEntityDTO getCategoryEntityById(@PathVariable Long id){
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(id);
        if(categoryEntity.isPresent()){
            return EntityToDTO(categoryEntity.get());
        }
        return null;
    }

    @PostMapping
    CategoryEntityDTO postCategoryEntity(@RequestBody CategoryEntityDTO categoryEntityDTO){
        categoryEntityService.saveCategoryEntity(DTOToEntity(categoryEntityDTO));
        return categoryEntityDTO;
    }


    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable Long id){
        categoryEntityService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    void putCategoryEntity(@PathVariable Long id, @RequestBody CategoryEntityDTO categoryEntityDTO){
        categoryEntityService.putCategory(id, DTOToEntity(categoryEntityDTO));
    }


}
