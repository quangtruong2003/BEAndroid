package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.CategoryDTO;
import com.quangtruong.be.entities.Category;
import com.quangtruong.be.services.CategoryService;
import com.quangtruong.be.services.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;


    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category category1 = categoryService.updateCategory(id, category);
            return new ResponseEntity<>(category1, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý exception ở đây, ví dụ: log lỗi, trả về message lỗi, ...
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Xử lý exception ở đây, ví dụ: log lỗi, trả về message lỗi, ...
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream().map(categoryServiceImpl::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) throws Exception {
        Category category = categoryService.findById(id);
        CategoryDTO categoryDTO = categoryServiceImpl.convertToDto(category);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
}