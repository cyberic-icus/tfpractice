package com.example.Shop.controllers;

import com.example.Shop.entities.CategoryEntity;
import com.example.Shop.repos.CategoryEntityRepository;
import com.example.Shop.services.CategoryEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryEntityController {
    @Autowired
    CategoryEntityService categoryEntityService;

    @GetMapping
    Iterable<CategoryEntity> getCategoryEntityAll(){
        return categoryEntityService.getCategoryAll();
    }

    @GetMapping("/{id}")
    Optional<CategoryEntity> getCategoryEntityById(@PathVariable Long id){
        return categoryEntityService.getCategoryById(id);
    }

    @PostMapping
    CategoryEntity postCategoryEntity(@RequestBody CategoryEntity categoryEntity){
        categoryEntityService.saveCategoryEntity(categoryEntity);
        return categoryEntity;
    }

    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable Long id){
        categoryEntityService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryEntity> putCategoryEntity(@PathVariable Long id, @RequestBody CategoryEntity categoryEntity){
        return categoryEntityService.putCategory(id, categoryEntity);
    }
}
