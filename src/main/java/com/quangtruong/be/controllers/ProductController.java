package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.ProductDTO;
import com.quangtruong.be.entities.Product;
import com.quangtruong.be.services.ProductService;
import com.quangtruong.be.services.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = productServiceImpl.toDtoList(products);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws Exception {
        Product product = productService.findById(id);
        ProductDTO productDTO = productServiceImpl.convertToDto(product);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//        Product savedProduct = productService.saveProduct(product);
//        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        return productService.getProductById(id)
//                .map(existingProduct -> {
//                    Product updatedProduct = productService.saveProduct(product);
//                    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws Exception {
//        productService.deleteProduct(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(required = false) String keyword,
                                                        @RequestParam(required = false) Long categoryId,
                                                        @RequestParam(required = false) Long supplierId) {
        List<Product> products = productService.searchProducts(keyword, categoryId, supplierId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}