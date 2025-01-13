package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.ProductDTO;
import com.quangtruong.be.entities.Category;
import com.quangtruong.be.entities.Product;
import com.quangtruong.be.entities.Supplier;
import com.quangtruong.be.repositories.ProductRepository;
import com.quangtruong.be.request.CreateProductRequest;
import com.quangtruong.be.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(CreateProductRequest req, Category category, Supplier supplier) {
        Product product = new Product();
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setDescription(req.getDescription());
        product.setImages(req.getImages());
        product.setProductName(req.getName());
        product.setUnitPrice(req.getPrice());
        product.setSpecifications(req.getSpecifications());
        product.setUnitsInStock(req.getUnitsInStock());
        product.setUnitsOnOrder(req.getUnitsOnOrder());
        product.setActive(req.isActive());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws Exception {
        Product product = findProductById(productId);
        productRepository.delete(product); // Sửa lại thành delete
    }

    @Override
    public List<Product> searchProducts(String keyword, Long categoryId, Long supplierId) {
        return productRepository.searchProducts(keyword, categoryId, supplierId);
    }

    @Override
    public Product findProductById(Long productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new Exception("product not exist...");
        }
        return optionalProduct.get();
    }

    @Override
    public Product updateProductStatus(Long productId) throws Exception {
        Product product = findProductById(productId);
        product.setActive(!product.isActive());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, CreateProductRequest req, Category category, Supplier supplier) throws Exception {
        Product product = findById(id);
        // Cập nhật các trường của product với req, category, supplier
        product.setProductName(req.getName());
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setUnitPrice(req.getPrice());
        product.setUnitsInStock(req.getUnitsInStock());
        product.setUnitsOnOrder(req.getUnitsOnOrder());
        product.setDescription(req.getDescription());
        product.setSpecifications(req.getSpecifications());
        product.setImages(req.getImages());
        product.setActive(req.isActive());

        return productRepository.save(product);
    }
    public Product findById(Long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found with id: " + id));
    }

    public ProductDTO convertToDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductID());
        dto.setProductName(product.getProductName());
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setSupplierId(product.getSupplier().getSupplierId());
        dto.setSupplierName(product.getSupplier().getSupplierName());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setUnitsInStock(product.getUnitsInStock());
        dto.setDescription(product.getDescription());
        dto.setImages(product.getImages());
        dto.setActive(product.isActive());
        return dto;
    }

    public List<ProductDTO> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByProductName(String productName) {
        return productRepository.findByProductName(productName).isPresent();
    }
}