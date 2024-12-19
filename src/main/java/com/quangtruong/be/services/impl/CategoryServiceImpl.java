package com.quangtruong.be.services.impl;

import com.quangtruong.be.entities.Category;
import com.quangtruong.be.repositories.CategoryRepository;
import com.quangtruong.be.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setCategoryName(updatedCategory.getCategoryName());
                    category.setParentCategory(updatedCategory.getParentCategory());
                    category.setDescription(updatedCategory.getDescription());
                    category.setCreatedAt(updatedCategory.getCreatedAt());
                    category.setUpdatedAt(updatedCategory.getUpdatedAt());
                    return categoryRepository.save(category);
                })
                .orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}