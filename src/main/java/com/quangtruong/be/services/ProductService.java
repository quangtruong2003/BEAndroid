package com.quangtruong.be.services;

import com.quangtruong.be.entities.Category;
import com.quangtruong.be.entities.Product;
import com.quangtruong.be.entities.Supplier;
import com.quangtruong.be.request.CreateProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(CreateProductRequest req, Category category, Supplier supplier);
    void deleteProduct(Long productId) throws Exception;
    List<Product> searchProducts(String keyword, Long categoryId, Long supplierId);
    Product findProductById(Long productId) throws Exception;
    Product updateProductStatus(Long productId) throws Exception; // Sửa tên method

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    Product updateProduct(Long id, CreateProductRequest req, Category category, Supplier supplier) throws Exception;

    Product findById(Long id) throws Exception;
}