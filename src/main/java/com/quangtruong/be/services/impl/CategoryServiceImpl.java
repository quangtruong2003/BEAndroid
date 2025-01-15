package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.CategoryDTO;
import com.quangtruong.be.entities.Category;
import com.quangtruong.be.repositories.CategoryRepository;
import com.quangtruong.be.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) throws Exception {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found with id: " + id));
    }

    @Override
    public CategoryDTO updateCategory(Long id, Category updatedCategory) throws Exception {
        Category category = findById(id);
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setParentCategory(updatedCategory.getParentCategory());
        category.setDescription(updatedCategory.getDescription());
        category.setUpdatedAt(LocalDateTime.now());
        return convertToDto(categoryRepository.save(category)); // Sửa ở đây
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO convertToDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        if (category.getParentCategory() != null) {
            dto.setParentCategoryId(category.getParentCategory().getCategoryId());
            dto.setParentCategoryName(category.getParentCategory().getCategoryName());
        }
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());

        dto.setProductCount(category.getProducts() != null ? category.getProducts().size() : 0);
        return dto;
    }

    @Override
    public List<CategoryDTO> toDtoList(List<Category> categories) {
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

}