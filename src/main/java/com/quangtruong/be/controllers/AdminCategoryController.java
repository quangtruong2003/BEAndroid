package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.CategoryDTO;
import com.quangtruong.be.entities.Category;
import com.quangtruong.be.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) throws Exception {
        Category category = categoryService.findById(id);
        CategoryDTO categoryDTO = categoryService.convertToDto(category);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category) {
        Category createdCategory = categoryService.saveCategory(category);
        CategoryDTO categoryDTO = categoryService.convertToDto(createdCategory);
        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) throws Exception {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}