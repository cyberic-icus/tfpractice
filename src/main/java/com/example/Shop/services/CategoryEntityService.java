package com.example.Shop.services;

import com.example.Shop.db.entities.CategoryRelatedEntities.CategoryEntity;
import com.example.Shop.db.repos.CategoryEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryEntityService {

    final private CategoryEntityRepository categoryEntityRepository;

    public CategoryEntityService(CategoryEntityRepository categoryEntityRepository) {
        this.categoryEntityRepository = categoryEntityRepository;
    }

    public void saveCategoryEntity(CategoryEntity categoryEntity) {
        if (categoryEntity != null) {
            categoryEntityRepository.save(categoryEntity);
        }
    }

    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryEntityRepository.findById(id);
    }

    public Iterable<CategoryEntity> getCategoryAll() {
        return categoryEntityRepository.findAll();
    }

    public void deleteCategory(Long id) {
        categoryEntityRepository.deleteById(id);
    }

    public ResponseEntity<CategoryEntity> putCategory(Long id, CategoryEntity categoryEntity) {
        return
                (categoryEntityRepository.existsById(id))
                        ? new ResponseEntity<>(categoryEntityRepository.save(categoryEntity), HttpStatus.CREATED)
                        : new ResponseEntity<>(categoryEntityRepository.save(categoryEntity),
                        HttpStatus.OK);
    }

}
