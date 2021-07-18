package com.example.Shop.controllers;

import com.example.Shop.db.dto.CategoryRelatedDTO.CategoryEntityDTO;
import com.example.Shop.db.entities.CategoryRelatedEntities.CategoryEntity;
import com.example.Shop.services.CategoryEntityService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Api
@RestController
@RequestMapping("/category")
@CrossOrigin(origins = {"http://localhost:4200/", "https://summer-practy.herokuapp.com/"}, maxAge = 3600)
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
        return StreamSupport.stream(categoryEntityService.getCategoryAll().spliterator(), false)
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    CategoryEntityDTO getCategoryEntityById(@PathVariable Long id) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(id);
        if(categoryEntity.isPresent()){
            return EntityToDTO(categoryEntity.get());
        }
        return null;
    }

    @PostMapping
    CategoryEntityDTO postCategoryEntity(@Valid @RequestBody CategoryEntityDTO categoryEntityDTO) {
        categoryEntityService.saveCategoryEntity(DTOToEntity(categoryEntityDTO));
        return categoryEntityDTO;
    }

    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable Long id) {
        categoryEntityService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    CategoryEntityDTO putCategoryEntity(@PathVariable Long id, @Valid @RequestBody CategoryEntityDTO categoryEntityDTO) {
        Optional<CategoryEntity> categoryEntity = categoryEntityService.getCategoryById(id);
        CategoryEntity newCategory = DTOToEntity(categoryEntityDTO);
        if(categoryEntity.isPresent()){
            CategoryEntity oldCategory = categoryEntity.get();

            oldCategory.setCategoryName(newCategory.getCategoryName());
            oldCategory.setCategoryDescription(newCategory.getCategoryDescription());

            return categoryEntityDTO;
        } else categoryEntityService.saveCategoryEntity(newCategory);
        return null;
    }
}