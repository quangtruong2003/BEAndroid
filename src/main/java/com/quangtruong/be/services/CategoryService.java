package com.quangtruong.be.services;

import com.quangtruong.be.dto.CategoryDTO;
import com.quangtruong.be.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category saveCategory(Category category);
    void deleteCategory(Long id) throws Exception;
    CategoryDTO updateCategory(Long id, Category category) throws Exception;
    Category findById(Long id) throws Exception;
    CategoryDTO convertToDto(Category category);
    List<CategoryDTO> toDtoList(List<Category> categories);
}