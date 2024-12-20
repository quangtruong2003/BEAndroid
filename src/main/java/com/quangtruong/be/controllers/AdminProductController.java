package com.quangtruong.be.controllers;

import com.quangtruong.be.entities.Category;
import com.quangtruong.be.entities.Product;
import com.quangtruong.be.entities.Supplier;
import com.quangtruong.be.request.CreateProductRequest;
import com.quangtruong.be.services.CategoryService;
import com.quangtruong.be.services.ProductService;
import com.quangtruong.be.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequest req) throws Exception {
        Category category = categoryService.findById(req.getCategoryId());
        Supplier supplier = supplierService.findById(req.getSupplierId());

        Product createdProduct = productService.createProduct(req, category, supplier);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductRequest req) throws Exception {
        Category category = categoryService.findById(req.getCategoryId());
        Supplier supplier = supplierService.findById(req.getSupplierId());
        Product updatedProduct = productService.updateProduct(id, req, category, supplier);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws Exception {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Product> updateProductStatus(@PathVariable Long id) throws Exception {
        Product updatedProduct = productService.updateProductStatus(id);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}